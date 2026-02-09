package com.jenga_marketplace.jenga_backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jenga_marketplace.jenga_backend.model.StandardizedLedger;
import com.jenga_marketplace.jenga_backend.model.enums.AccountType;



@Repository
public interface StandardizedLedgerRepository extends JpaRepository<StandardizedLedger, Long> {
    
    /**
     * Retrieves all ledger entries for a specific SME within a date range.
     * Essential for generating monthly or annual Income Statements.
     */
    List<StandardizedLedger> findByProfileIdAndEntryDateBetween(
        Long profileId, LocalDateTime start, LocalDateTime end);

    /**
     * Professional Step: Optimized query to sum amounts by AccountType.
     * Reduces processing load on the Java Service layer.
     */
    @Query("SELECT SUM(l.amount) FROM StandardizedLedger l " +
           "WHERE l.profile.id = :profileId AND l.accountType = :type " +
           "AND l.entryDate BETWEEN :start AND :end")
    java.math.BigDecimal sumByAccountType(Long profileId, AccountType type, LocalDateTime start, LocalDateTime end);
}