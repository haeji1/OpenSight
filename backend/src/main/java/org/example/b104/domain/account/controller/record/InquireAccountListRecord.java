package org.example.b104.domain.account.controller.record;

public record InquireAccountListRecord(
        String bankCode,
        String bankName,
        String userName,//명세서엔 username이라고 써있음
        String accountNo,
        String accountName,
        String accountTypeCode,
        String accountTypeName,
        String accountCreatedDate,
        String accountExpiryDate,
        long dailyTransferLimit,
        long oneTimeTransferLimit,
        long accountBalance,
        String lastTransactionDate
) {
}
