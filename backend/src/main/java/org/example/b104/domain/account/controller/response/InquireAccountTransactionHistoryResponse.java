package org.example.b104.domain.account.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.example.b104.domain.account.controller.record.InquireAccountHistoryRecord;

@Builder
@Getter
@AllArgsConstructor
@ToString
public class InquireAccountTransactionHistoryResponse {
    private String result;
    private AccountResponseHeader Header;
    private InquireAccountHistoryRecord REC;
}
