package com.jenga_marketplace.jenga_backend.security;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filter to intercept every request and validate the JWT token.
 * This is the bridge between React's 'Bearer' token and Spring Security.
 */
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        // 1. Extract the Authorization header from the React request
        String authHeader = request.getHeader("Authorization");

        // 2. Check if the header contains a Bearer token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Remove "Bearer " prefix
            
            try {
                // 3. Extract email (subject) from the token using JwtUtil
                String email = jwtUtil.extractEmail(token);

                // 4. If email is valid and user is not already authenticated in this thread
                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    
                    // Create an authentication object that Spring Security understands
                    UsernamePasswordAuthenticationToken authToken = 
                        new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>());
                    
                    // 5. Log the user into the Security Context. 
                    // This makes the 'Principal' available in your Controllers.
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } catch (Exception e) {
                // If token is invalid or expired, we simply don't set the authentication.
                // Spring Security will then return a 401/403 for protected paths.
                logger.error("Could not set user authentication: " + e.getMessage());
            }
        }
        
        // 6. Continue to the next filter in the chain (or the Controller)
        filterChain.doFilter(request, response);
    }
}