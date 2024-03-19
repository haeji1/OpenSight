package org.example.b104.domain.account.controller.record;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


public record InquireAccountBalanceRecord(String bankCode, String accountNo, int accountBalance, String accountCreatedDate, String accountExpiryDate, String lastTransactionDate) {}
