package com.jenga_marketplace.jenga_backend.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.jenga_marketplace.jenga_backend.model.FinancialOnboarding;
import com.jenga_marketplace.jenga_backend.model.enums.AccountType;
import com.jenga_marketplace.jenga_backend.repository.FinancialOnboardingRepository;
import com.jenga_marketplace.jenga_backend.repository.StandardizedLedgerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FinancialReportingService {

    private final StandardizedLedgerRepository ledgerRepository;
    private final FinancialOnboardingRepository onboardingRepository;

    /**
     * FIX: Implemented generateBalanceSheet to resolve Controller error.
     * Logic: Balance Sheet = Opening Balances + Dynamic Net Movement.
     */
    public Map<String, BigDecimal> generateBalanceSheet(Long profileId) {
        FinancialOnboarding opening = onboardingRepository.findByProfileId(profileId);
        
        // Sum current movements from ledger
        BigDecimal currentCashMovement = ledgerRepository.sumByAccountType(profileId, AccountType.REVENUE, LocalDateTime.MIN, LocalDateTime.now())
                .subtract(ledgerRepository.sumByAccountType(profileId, AccountType.COGS, LocalDateTime.MIN, LocalDateTime.now()))
                .subtract(ledgerRepository.sumByAccountType(profileId, AccountType.OPERATING_EXPENSE, LocalDateTime.MIN, LocalDateTime.now()));

        // Map IFRS Assets, Liabilities, and Equity
        Map<String, BigDecimal> balanceSheet = new HashMap<>();
        balanceSheet.put("Total_Assets", opening.getOpeningCashOnHand().add(currentCashMovement).add(opening.getOpeningInventoryValue()));
        balanceSheet.put("Total_Liabilities", opening.getOpeningLoans().add(opening.getOpeningTradePayables()));
        balanceSheet.put("Owners_Equity", opening.getInitialCapital());

        return balanceSheet;
    }

    public Map<String, BigDecimal> generateIncomeStatement(Long profileId, LocalDateTime start, LocalDateTime end) {
        BigDecimal totalRevenue = ledgerRepository.sumByAccountType(profileId, AccountType.REVENUE, start, end);
        BigDecimal totalCogs = ledgerRepository.sumByAccountType(profileId, AccountType.COGS, start, end);
        BigDecimal totalExpenses = ledgerRepository.sumByAccountType(profileId, AccountType.OPERATING_EXPENSE, start, end);

        totalRevenue = (totalRevenue != null) ? totalRevenue : BigDecimal.ZERO;
        totalCogs = (totalCogs != null) ? totalCogs : BigDecimal.ZERO;
        totalExpenses = (totalExpenses != null) ? totalExpenses : BigDecimal.ZERO;

        BigDecimal grossProfit = totalRevenue.subtract(totalCogs);
        BigDecimal netIncome = grossProfit.subtract(totalExpenses);

        Map<String, BigDecimal> statement = new HashMap<>();
        statement.put("Total_Revenue", totalRevenue);
        statement.put("Cost_of_Goods_Sold", totalCogs);
        statement.put("Gross_Profit", grossProfit);
        statement.put("Operating_Expenses", totalExpenses);
        statement.put("Net_Income", netIncome);

        return statement;
    }
}