package com.jenga_marketplace.jenga_backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Final DTO for Jenga Marketplace Registration.
 * Captures standard user data plus SME-specific business metadata.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    // --- Standard User Fields ---
    private String name;
    private String email;
    private String password;
    private String phone;
    private String role; // BUYER or SELLER

    // --- SME Business Strategy Fields ---
    
    // Captures the shop name (e.g., "Nyamakima Hardware")
    private String businessName;

    // Identifies if they are a Sole Proprietor or Limited Company
    private String businessType;

    // The 'Tax Buffer' field - Optional KRA PIN for the Private Estimator
    private String kraPin;

    // Physical shop location for the "Verified" badge process
    private String shopLocation;
}