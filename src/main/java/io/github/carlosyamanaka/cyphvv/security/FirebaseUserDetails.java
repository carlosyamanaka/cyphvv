package io.github.carlosyamanaka.cyphvv.security;

import java.util.Map;

public class FirebaseUserDetails {

    private final String uid;
    private final String email;
    private final String name;
    private final Map<String, Object> claims;

    public FirebaseUserDetails(String uid, String email, String name, Map<String, Object> claims) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.claims = claims;
    }

    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public Map<String, Object> getClaims() {
        return claims;
    }

    @Override
    public String toString() {
        return "FirebaseUserDetails{" +
                "uid='" + uid + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
