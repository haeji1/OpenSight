package org.example.b104.domain.account.controller.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class AccountTransferRequest {
    AccountRequestHeader accountRequestHeader;
    String depositBankCode;
    String depositAccountNo;
    int transactionBalance;
    String transactionUniqueNo;
    String withdrawalBankCode;
    String withdrawalAccountNo;
    String depositTransactionSummary;
    String withdrawalTransactionSummary;
}
