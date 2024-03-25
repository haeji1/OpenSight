package org.example.b104.domain.account.service.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class DrawingTransferCommand {
    String bankCode;
    String accountNo;
    long transactionBalance;
    String transactionSummary;
    String userKey;
}
