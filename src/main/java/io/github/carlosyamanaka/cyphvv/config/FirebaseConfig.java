package io.github.carlosyamanaka.cyphvv.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import jakarta.annotation.PostConstruct;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.credentials.path:firebase-credentials.json}")
    private String firebaseCredentialsPath;

    @PostConstruct
    public void initialize() {
        if (FirebaseApp.getApps().isEmpty()) {
            try {
                ClassPathResource resource = new ClassPathResource(firebaseCredentialsPath);

                if (!resource.exists()) {
                    System.err.println("⚠️  Firebase credentials file not found: " + firebaseCredentialsPath);
                    System.err.println(
                            "⚠️  Firebase authentication will be disabled. Set FIREBASE_CREDENTIALS_PATH environment variable.");
                    return;
                }

                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(resource.getInputStream()))
                        .build();

                FirebaseApp.initializeApp(options);
                System.out.println("✅ Firebase Admin SDK initialized successfully");
            } catch (IOException e) {
                System.err.println("❌ Error initializing Firebase Admin SDK: " + e.getMessage());
                System.err.println("⚠️  Firebase authentication will be disabled");
            }
        }
    }
}
