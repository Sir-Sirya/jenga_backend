package com.jenga_marketplace.jenga_backend.model.dto;

public class LoginResponse {
    private String message;
    private Long userId;
    private String name;
    private String role;
    private String token; // We'll add this critical field for JWT

    public LoginResponse(String message, Long userId, String name, String role, String token) {
        this.message = message;
        this.userId = userId;
        this.name = name;
        this.role = role;
        this.token = token;
    }

    // Getters and Setters
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}