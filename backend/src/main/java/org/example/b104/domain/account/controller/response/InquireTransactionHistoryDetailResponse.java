package org.example.b104.domain.account.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.example.b104.domain.account.controller.record.InquireTransactionHistoryDetailRecord;

@Builder
@Getter
@AllArgsConstructor
@ToString
public class InquireTransactionHistoryDetailResponse {
    private String result;
    @JsonProperty("Header")
    private AccountResponseHeader Header;
    @JsonProperty("REC")
    private InquireTransactionHistoryDetailRecord REC;
}
