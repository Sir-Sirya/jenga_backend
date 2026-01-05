package com.jenga_marketplace.jenga_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.jenga_marketplace.jenga_backend.model.dto.LoginRequest;
import com.jenga_marketplace.jenga_backend.model.dto.RegisterRequest;
import com.jenga_marketplace.jenga_backend.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*") // Essential for resolving the frontend Network Error
public class AuthController {

    private final AuthService authService;

    // Adding @Autowired here ensures Spring injects the service correctly
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) { 
        return authService.register(request);
    } 

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) { 
        return authService.login(request);
    }
}