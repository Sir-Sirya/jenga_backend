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

/**
 * SecurityConfig: The gatekeeper for the Jenga Marketplace.
 * Manages access control, JWT validation, and Cross-Origin Resource Sharing (CORS).
 */
@Configuration
public class SecurityConfig {

    private final JwtUtil jwtUtil;

    public SecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Stateless APIs do not require CSRF
            
            /**
             * CORS CONFIGURATION:
             * Linked to the bean below to resolve '403 Forbidden' pre-flight errors.
             */
            .cors(Customizer.withDefaults()) 
            
            .authorizeHttpRequests(auth -> auth
                /**
                 * PUBLIC ACCESS: 
                 * Permitting auth, products, and categories ensures buyers can browse 
                 * and new SMEs can register without a token.
                 */
                .requestMatchers("/api/auth/**", "/api/products/**", "/api/categories/**").permitAll()
                
                /**
                 * SECURE ACCESS: 
                 * Requires a valid JWT to access sensitive SME metadata or POS cart logic.
                 */
                .requestMatchers("/api/users/me").authenticated() 
                .requestMatchers("/api/cart/**").authenticated() 
                
                .anyRequest().authenticated() 
            )
            
            /**
             * STATELESS SESSION: 
             * Prevents sticky sessions by relying entirely on JWT validation.
             */
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            /**
             * THE JWT BRIDGE: 
             * Validates the bearer token before Spring evaluates authorization.
             */
            .addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * CORS CONFIGURATION:
     * Fixes "No 'Access-Control-Allow-Origin' header" errors.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        /**
         * FIX: Added local IP address to allowed origins.
         * This resolves the CORS block occurring at http://192.168.1.11:8081.
         */
        configuration.setAllowedOrigins(Arrays.asList(
            "http://localhost:5173", 
            "http://localhost:8081",
            "http://192.168.1.11:8081" 
        ));
        
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Cache-Control"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}