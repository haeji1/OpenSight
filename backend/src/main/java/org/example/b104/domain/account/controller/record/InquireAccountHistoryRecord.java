package org.example.b104.domain.account.controller.record;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;


public record InquireAccountHistoryRecord(

        int totalCount,

        SingleAccountTransactionHistory[] transactionHistory
) {
}
