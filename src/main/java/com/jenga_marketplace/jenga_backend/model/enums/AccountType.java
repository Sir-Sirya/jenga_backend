package com.jenga_marketplace.jenga_backend.model.enums;

/**
 * Maps transactions to the five elements of financial statements defined by IFRS.
 */
public enum AccountType {
    REVENUE,           // Sales from hardware (timber, cement, tools)
    COGS,              // Cost of Goods Sold
    OPERATING_EXPENSE, // Rent, electricity, labor
    ASSET,             // Purchase of new equipment or inventory
    LIABILITY,         // Loan repayments or supplier payments
    EQUITY             // Capital injections or withdrawals
}