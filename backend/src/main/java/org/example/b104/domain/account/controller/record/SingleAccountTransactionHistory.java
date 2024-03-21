package org.example.b104.domain.account.controller.record;


public record SingleAccountTransactionHistory(
        int transactionUniqueNo,
        String transactionDate,
        String transactionTime,
        String transactionType,
        String transactionTypeName,
        String transactionAccountNo,
        long transactionBalance,
        long transactionAfterBalance,
        String transactionSummary
) {
}
