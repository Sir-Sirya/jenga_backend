package com.jenga_marketplace.jenga_backend.service;

import com.jenga_marketplace.jenga_backend.model.dto.BusinessOnboardingRequest;
import com.jenga_marketplace.jenga_backend.model.BusinessProfile;
import com.jenga_marketplace.jenga_backend.model.FinancialOnboarding;
import com.jenga_marketplace.jenga_backend.model.User;
import com.jenga_marketplace.jenga_backend.repository.BusinessProfileRepository;
import com.jenga_marketplace.jenga_backend.repository.FinancialOnboardingRepository;
import com.jenga_marketplace.jenga_backend.repository.UserRepository; // Assuming you have a standard UserRepository
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * Service to manage the SME's lifecycle from onboarding to financial state management.
 * Addresses the infrastructure gap by providing simplified, automated entry points.
 */
@Service
@RequiredArgsConstructor
public class BusinessProfileService {

    private final BusinessProfileRepository profileRepository;
    private final FinancialOnboardingRepository onboardingRepository;
    private final UserRepository userRepository;

    /**
     * Professional Step: Comprehensive Onboarding.
     * Creates both the identity profile and the opening financial balances in one transaction.
     */
    @Transactional
    public BusinessProfile createFullProfile(Long userId, BusinessOnboardingRequest request) {
        // 1. Validate and Fetch User
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found for onboarding"));

        // 2. Map Identity to BusinessProfile
        // This establishes the legal entity used for trust and verification.
        BusinessProfile profile = BusinessProfile.builder()
                .user(user)
                .businessName(request.getBusinessName())
                .businessType(request.getBusinessType())
                .registrationNumber(request.getRegistrationNumber())
                .location(request.getLocation())
                .currency("KES")
                .reportingStandard("IFRS_FOR_SMES")
                .build();

        BusinessProfile savedProfile = profileRepository.save(profile);

        // 3. Map Opening Balances to FinancialOnboarding
        // Provides the 'Baseline' for IFRS Statement of Financial Position (Balance Sheet).
        FinancialOnboarding onboarding = FinancialOnboarding.builder()
                .profile(savedProfile)
                .asOfDate(LocalDate.now()) // The date the hardware shop went digital
                .openingCashOnHand(request.getOpeningCash())
                .openingInventoryValue(request.getOpeningInventory())
                .openingEquipmentValue(request.getOpeningEquipment())
                .openingTradePayables(request.getOpeningPayables())
                .openingLoans(request.getOpeningLoans())
                .initialCapital(request.getInitialCapital())
                .retainedEarnings(java.math.BigDecimal.ZERO) // New SMEs start with zero retained earnings in the system
                .build();

        onboardingRepository.save(onboarding);

        return savedProfile;
    }

    /**
     * Helper to retrieve profile for dashboard displays.
     */
    public BusinessProfile getProfileByUserId(Long userId) {
        return profileRepository.findByUserId(userId);
    }
}