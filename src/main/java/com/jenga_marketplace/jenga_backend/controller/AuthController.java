package com.jenga_marketplace.jenga_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController; // Added for input validation

import com.jenga_marketplace.jenga_backend.model.dto.LoginRequest;
import com.jenga_marketplace.jenga_backend.model.dto.RegisterRequest;
import com.jenga_marketplace.jenga_backend.service.AuthService;

import jakarta.validation.Valid;

/**
 * Controller for Jenga Marketplace Authentication.
 * Handles specialized onboarding for Hardware SMEs.
 */
@RestController
@RequestMapping("/api/auth")
// Origins allowed for both Vite (5173) and your production port (8081)
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8081"})
public class AuthController {

    private final AuthService authService;

    // Constructor injection is the recommended way to handle @Autowired services
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * POST /api/auth/register
     * Handles the Jenga "SME-First" onboarding process.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) { 
        // Passes the full request including KRA PIN and Business Name to the service
        return authService.register(request);
    } 

    /**
     * POST /api/auth/login
     * Returns a JWT and the User object for immediate frontend sync.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) { 
        return authService.login(request);
    }
}