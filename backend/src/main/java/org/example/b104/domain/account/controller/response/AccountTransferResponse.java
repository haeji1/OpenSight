package org.example.b104.domain.account.controller.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.example.b104.domain.account.controller.record.AccountTransferRecord;

@Builder
@Getter
@AllArgsConstructor
@ToString
public class AccountTransferResponse {
    String result;
    String errorCode;
    String errorMessage;
    AccountResponseHeader Header;
    AccountTransferRecord[] REC;
}
