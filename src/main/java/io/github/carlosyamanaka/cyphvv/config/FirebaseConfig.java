package io.github.carlosyamanaka.cyphvv.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.config.path:firebase-credentials.json}")
    private String firebaseCredentialsPath;

    @PostConstruct
    public void initialize() {
        if (FirebaseApp.getApps().isEmpty()) {
            try {
                InputStream inputStream;
                File file = new File(firebaseCredentialsPath);
                File absoluteSecretFile = new File("/etc/secrets", file.getName());
                File relativeSecretFile = new File("etc/secrets", file.getName());
                if (file.exists() && file.isFile()) {
                    inputStream = new FileInputStream(file);
                    System.out.println("ℹ️ Loading Firebase credentials from external file: " + file.getAbsolutePath());
                } else if (absoluteSecretFile.exists() && absoluteSecretFile.isFile()) {
                    inputStream = new FileInputStream(absoluteSecretFile);
                    System.out.println("ℹ️ Loading Firebase credentials from absolute secret path: " + absoluteSecretFile.getAbsolutePath());
                } else if (relativeSecretFile.exists() && relativeSecretFile.isFile()) {
                    inputStream = new FileInputStream(relativeSecretFile);
                    System.out.println("ℹ️ Loading Firebase credentials from relative secret path: " + relativeSecretFile.getAbsolutePath());
                } else {
                    ClassPathResource resource = new ClassPathResource(firebaseCredentialsPath);
                    if (!resource.exists()) {
                        System.err.println("⚠️  Firebase credentials file not found: " + firebaseCredentialsPath);
                        System.err.println(
                                "⚠️  Firebase authentication will be disabled. Set FIREBASE_CREDENTIALS_PATH environment variable.");
                        return;
                    }
                    inputStream = resource.getInputStream();
                    System.out.println("ℹ️ Loading Firebase credentials from classpath: " + firebaseCredentialsPath);
                }

                try (InputStream is = inputStream) {
                    FirebaseOptions options = FirebaseOptions.builder()
                            .setCredentials(GoogleCredentials.fromStream(is))
                            .build();

                    FirebaseApp.initializeApp(options);
                    System.out.println("✅ Firebase Admin SDK initialized successfully");
                }
            } catch (IOException e) {
                System.err.println("❌ Error initializing Firebase Admin SDK: " + e.getMessage());
                System.err.println("⚠️  Firebase authentication will be disabled");
            }
        }
    }
}
