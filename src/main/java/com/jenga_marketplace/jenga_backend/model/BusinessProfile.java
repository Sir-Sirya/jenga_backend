package com.jenga_marketplace.jenga_backend.model;

import com.jenga_marketplace.jenga_backend.model.enums.BusinessType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "business_profiles")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Links the profile to your existing User system
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String businessName;

    @Enumerated(EnumType.STRING)
    private BusinessType businessType;

    @Column(unique = true)
    private String registrationNumber; // KRA PIN or Business ID

    private String location; // County/City mapping for local trade [cite: 1924]

    @Builder.Default
    private String currency = "KES"; // Default for Kenyan Market [cite: 1533]
    
    @Builder.Default
    private String reportingStandard = "IFRS_FOR_SMES"; // Global standard compliance [cite: 1497]
}