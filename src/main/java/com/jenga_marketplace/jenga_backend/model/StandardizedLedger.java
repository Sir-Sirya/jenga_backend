package com.jenga_marketplace.jenga_backend.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.jenga_marketplace.jenga_backend.model.enums.AccountType;
import com.jenga_marketplace.jenga_backend.model.enums.CashFlowCategory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "standardized_ledger")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StandardizedLedger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = false)
    private BusinessProfile profile;

    // Link to original transaction (e.g., Order ID or Expense ID)
    private Long sourceTransactionId;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    private CashFlowCategory cashFlowCategory;

    @Column(nullable = false)
    private BigDecimal amount;

    private String description;

    // Specifically for Kenya: Tracks M-Pesa references for easy reconciliation [cite: 2891]
    private String mPesaReference;

    @Column(nullable = false)
    private LocalDateTime entryDate;
}