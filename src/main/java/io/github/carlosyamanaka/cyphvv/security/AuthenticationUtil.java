package io.github.carlosyamanaka.cyphvv.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationUtil {

    public static FirebaseUserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof FirebaseUserDetails) {
            return (FirebaseUserDetails) authentication.getPrincipal();
        }

        return null;
    }

    public static String getCurrentUserId() {
        FirebaseUserDetails user = getCurrentUser();
        return user != null ? user.getUid() : null;
    }

    public static boolean isAuthenticated() {
        return getCurrentUser() != null;
    }
}
