package com.jenga_marketplace.jenga_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jenga_marketplace.jenga_backend.model.BusinessProfile;

@Repository
public interface BusinessProfileRepository extends JpaRepository<BusinessProfile, Long> {
    // Finds profile by User ID to link onboarding with the logged-in seller
    BusinessProfile findByUserId(Long userId);
}
