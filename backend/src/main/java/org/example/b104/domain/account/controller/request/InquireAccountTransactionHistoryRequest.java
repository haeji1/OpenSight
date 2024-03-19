package org.example.b104.domain.account.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class InquireAccountTransactionHistoryRequest {
    AccountRequestHeader Header;
    String bankCode;
    String accountNo;
    String startDate;
    String endDate;
    String transactionType;
    String orderByType;
}
