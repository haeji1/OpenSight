package org.example.b104.domain.account.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@AllArgsConstructor
public class DrawingTransferRequest {
    AccountRequestHeader Header;
    String bankCode;
    String accountNo;
    int transactionBalance;
    String transactionSummary;
}
