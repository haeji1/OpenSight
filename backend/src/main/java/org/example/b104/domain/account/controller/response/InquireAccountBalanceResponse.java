package org.example.b104.domain.account.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.example.b104.domain.account.controller.record.DrawingTransferRecord;
import org.example.b104.domain.account.controller.record.InquireAccountBalanceRecord;

@Builder
@Getter
@ToString
@AllArgsConstructor
public class InquireAccountBalanceResponse {
    String result;
    AccountResponseHeader Header;
    InquireAccountBalanceRecord REC;
}
