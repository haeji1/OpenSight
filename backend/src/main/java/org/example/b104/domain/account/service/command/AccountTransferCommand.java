package org.example.b104.domain.account.service.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class AccountTransferCommand {
    String depositBankCode;
    String depositAccountNo;
    int transactionBalance;
    String withdrawalBankCode;
    String withdrawalAccountNo;
    String depositTransactionSummary;
    String withdrawalTransactionSummary;
    String userKey;

}
