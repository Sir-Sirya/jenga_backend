package com.jenga_marketplace.jenga_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Keep this disabled for your API prototype
            .cors(cors -> cors.disable()) // Temporarily disable CORS logic to test the URL directly
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/products/**").permitAll() // Open the products door
                .requestMatchers("/api/auth/**").permitAll()
                .anyRequest().permitAll() // CHANGE THIS TEMPORARILY to permitAll() to test
            )
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}