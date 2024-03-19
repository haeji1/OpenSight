package org.example.b104.domain.account.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.example.b104.domain.account.controller.record.ReceivedTransferAccountNumberRecord;

@Builder
@Getter
@AllArgsConstructor
@ToString
public class ReceivedTransferAccountNumberResponse {
    String result;
    AccountResponseHeader Header;
    ReceivedTransferAccountNumberRecord REC;
}
