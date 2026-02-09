package com.jenga_marketplace.jenga_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jenga_marketplace.jenga_backend.model.FinancialOnboarding;

@Repository
public interface FinancialOnboardingRepository extends JpaRepository<FinancialOnboarding, Long> {
    FinancialOnboarding findByProfileId(Long profileId);
}