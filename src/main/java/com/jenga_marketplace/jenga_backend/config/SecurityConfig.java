package com.jenga_marketplace.jenga_backend.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.jenga_marketplace.jenga_backend.security.JwtFilter;
import com.jenga_marketplace.jenga_backend.security.JwtUtil;

@Configuration
public class SecurityConfig {

    private final JwtUtil jwtUtil;

    public SecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            // Link the specific CORS bean defined below to prevent 403 blocks
            .cors(Customizer.withDefaults()) 
            .authorizeHttpRequests(auth -> auth
                // Public paths for onboarding and browsing hardware
                .requestMatchers("/api/auth/**", "/api/products/**", "/api/categories/**").permitAll()
                // Private path for the SME Tax Buffer and Profile
                .requestMatchers("/api/users/me").authenticated() 
                .anyRequest().authenticated() 
            )
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // The critical bridge: validates the JWT before Spring checks authorization
            .addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Define exactly which frontends can talk to this backend.
     * This fixes the "CORS/Forbidden" issues seen in your console.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Allow both your Vite and production ports
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173", "http://localhost:8081"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Cache-Control"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}