package org.example.b104.domain.account.service.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class InquireAccountHistoryTransactionCommand {
    String bankCode;
    String accountNo;
    String startDate;
    String endDate;
    String transactionType;
    String orderByType;
    String userKey;
}
