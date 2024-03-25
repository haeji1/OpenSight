package org.example.b104.domain.account.controller.clirequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor

public class cliReceivedTransferAccountNumberRequest {
    private String bankCode;
    private String accountNo;
    private Long transactionBalance;
    private String transactionSummary;
}
