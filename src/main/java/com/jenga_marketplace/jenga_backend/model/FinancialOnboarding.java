package com.jenga_marketplace.jenga_backend.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "financial_onboarding")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinancialOnboarding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "profile_id", nullable = false)
    private BusinessProfile profile;

    @Column(nullable = false)
    private LocalDate asOfDate; // Date the seller started using Jenga

    // --- ASSETS (Opening) ---
    private BigDecimal openingCashOnHand; // Cash in till + M-Pesa business balance [cite: 2586, 2742]
    private BigDecimal openingInventoryValue; // Value of physical stock (timber, cement, etc.)
    private BigDecimal openingEquipmentValue; // Value of non-current assets (machinery, trucks)

    // --- LIABILITIES (Opening) ---
    private BigDecimal openingTradePayables; // Debt owed to hardware suppliers
    private BigDecimal openingLoans; // Outstanding bank or micro-finance debt

    // --- EQUITY (Opening) ---
    private BigDecimal initialCapital; // The owner's initial investment
    private BigDecimal retainedEarnings; // Accumulated profits from previous years
}