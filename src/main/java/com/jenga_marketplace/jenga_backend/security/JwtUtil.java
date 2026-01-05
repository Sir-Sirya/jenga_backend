package com.jenga_marketplace.jenga_backend.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    // Ensure this string is at least 32 characters long
    private final String SECRET_STRING = "MY_VERY_SECRET_KEY_123_ENSURE_IT_IS_LONG_ENOUGH";
    
    // Create a secure Key object to solve the signWith deprecation
    private final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET_STRING.getBytes(StandardCharsets.UTF_8));

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email) // Reverted to setSubject for older JJWT versions
                .setIssuedAt(new Date()) 
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
                .signWith(SECRET_KEY) // Still uses the secure Key object
                .compact();
    }
}