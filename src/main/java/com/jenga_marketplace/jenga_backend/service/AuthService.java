package com.jenga_marketplace.jenga_backend.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jenga_marketplace.jenga_backend.model.User;
import com.jenga_marketplace.jenga_backend.model.User.Role; // Ensure Role enum is accessible
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

    // ADD THIS METHOD - It was missing in your last snippet!
    public ResponseEntity<?> register(RegisterRequest request) {
        Optional<User> existing = userRepository.findByEmail(request.getEmail());
        if (existing.isPresent()) {
            return ResponseEntity.badRequest().body("Email already in use");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        // Set default role or one from request
        if (request.getRole() != null) {
            user.setRole(Role.valueOf(request.getRole().toUpperCase()));
        } else {
            user.setRole(Role.BUYER);
        }

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    public ResponseEntity<?> login(LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        
        if (userOpt.isEmpty() || !passwordEncoder.matches(request.getPassword(), userOpt.get().getPasswordHash())) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }

        String token = jwtUtil.generateToken(userOpt.get().getEmail());

        return ResponseEntity.ok(Map.of(
            "token", token,
            "message", "Login successful",
            "role", userOpt.get().getRole()
        ));
    }
}