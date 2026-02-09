package com.jenga_marketplace.jenga_backend.controller;

import com.jenga_marketplace.jenga_backend.service.FinancialReportingService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class FinancialReportingController {

    private final FinancialReportingService reportingService;

    /**
     * Generates the IFRS Income Statement (Statement of Profit or Loss).
     * Value Add: Standardized format ready for Kenyan bank loan applications.
     */
    @GetMapping("/income-statement/{profileId}")
    public ResponseEntity<Map<String, ?>> getIncomeStatement(
            @PathVariable Long profileId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        
        return ResponseEntity.ok(reportingService.generateIncomeStatement(profileId, start, end));
    }

    /**
     * Generates the Statement of Financial Position (Balance Sheet).
     * Combines Opening Balances + Dynamic Ledger activity.
     */
    @GetMapping("/balance-sheet/{profileId}")
    public ResponseEntity<Map<String, ?>> getBalanceSheet(@PathVariable Long profileId) {
        return ResponseEntity.ok(reportingService.generateBalanceSheet(profileId));
    }
}