package com.jenga_marketplace.jenga_backend.model.dto;


import java.math.BigDecimal;

import com.jenga_marketplace.jenga_backend.model.enums.BusinessType;

import lombok.Data;

/**
 * Combined DTO for the stepped onboarding process.
 * Used when a Kenyan SME "Sets up Business Profile".
 */
@Data
public class BusinessOnboardingRequest {
    // Identity Info
    private String businessName;
    private BusinessType businessType;
    private String registrationNumber;
    private String location;

    // Opening Financial Position (IFRS Requirement)
    private BigDecimal openingCash;
    private BigDecimal openingInventory;
    private BigDecimal openingEquipment;
    private BigDecimal openingPayables;
    private BigDecimal openingLoans;
    private BigDecimal initialCapital;
}
