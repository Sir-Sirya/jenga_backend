package com.jenga_marketplace.jenga_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jenga_marketplace.jenga_backend.model.dto.LoginRequest;
import com.jenga_marketplace.jenga_backend.model.dto.RegisterRequest;
import com.jenga_marketplace.jenga_backend.service.AuthService;

import jakarta.validation.Valid;

/**
 * Controller for Jenga Marketplace Authentication.
 * Handles specialized onboarding for hardware SMEs and Admin accounts.
 */
@RestController
@RequestMapping("/api/auth")
/* * Allowed Origins: 
 * - http://localhost:5173 (React/Vite Frontend)
 * - http://localhost:8081 (Alternative Backend/Testing Port)
 */
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8081"})
public class AuthController {

    private final AuthService authService;

    // Constructor injection is the professional standard for Spring dependency management.
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * POST /api/auth/register
     * Processes new Jenga accounts, including the newly added 'nationality' field.
     * * @param request Validated DTO containing user details, business info, and role.
     * @return ResponseEntity with the created User object or an error message.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) { 
        /* * The service layer handles BCrypt hashing of the password before saving 
         * to the 'users' table.
         */
        return authService.register(request);
    } 

    /**
     * POST /api/auth/login
     * Authenticates users and returns a JWT for frontend state synchronization.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) { 
        return authService.login(request);
    }
}