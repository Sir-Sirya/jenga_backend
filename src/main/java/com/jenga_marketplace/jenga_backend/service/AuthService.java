package com.jenga_marketplace.jenga_backend.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jenga_marketplace.jenga_backend.model.User;
import com.jenga_marketplace.jenga_backend.model.User.Role;
import com.jenga_marketplace.jenga_backend.model.dto.LoginRequest;
import com.jenga_marketplace.jenga_backend.model.dto.RegisterRequest;
import com.jenga_marketplace.jenga_backend.repository.UserRepository;
import com.jenga_marketplace.jenga_backend.security.JwtUtil;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * Specialized Register: Captures SME Business metadata and Tax Buffer fields.
     */
    public ResponseEntity<?> register(RegisterRequest request) {
        Optional<User> existing = userRepository.findByEmail(request.getEmail());
        if (existing.isPresent()) {
            return ResponseEntity.badRequest().body("Email already in use");
        }

        // Build the user with SME fields
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .businessName(request.getBusinessName()) // SME Differentiator
                .businessType(request.getBusinessType())
                .kraPin(request.getKraPin()) // Tax Buffer Logic
                .verified(false) // Default to unverified until admin review
                .build();

        // Handle Role Assignment
        if (request.getRole() != null) {
            try {
                user.setRole(Role.valueOf(request.getRole().toUpperCase()));
            } catch (IllegalArgumentException e) {
                user.setRole(Role.BUYER);
            }
        } else {
            user.setRole(Role.BUYER);
        }

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully. Welcome to Jenga!");
    }

    /**
     * Professional Login: Returns JWT + Full User Object for AuthContext sync.
     */
    public ResponseEntity<?> login(LoginRequest request) {
    Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
    
    if (userOpt.isPresent() && passwordEncoder.matches(request.getPassword(), userOpt.get().getPasswordHash())) {
        String token = jwtUtil.generateToken(userOpt.get().getEmail());
        
        // Return full user object for the "Please Log In" fix
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", userOpt.get()); 
        
        return ResponseEntity.ok(response);
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
}