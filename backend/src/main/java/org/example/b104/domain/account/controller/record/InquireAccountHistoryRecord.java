package org.example.b104.domain.account.controller.record;

import com.fasterxml.jackson.annotation.JsonProperty;

public record InquireAccountHistoryRecord(
        @JsonProperty("totalCount")
        int totalCount,
        @JsonProperty("list")
        SingleAccountTransactionHistory[] transactionHistory
) {
}
