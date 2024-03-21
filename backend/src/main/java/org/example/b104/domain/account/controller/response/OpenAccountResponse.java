package org.example.b104.domain.account.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@AllArgsConstructor
@ToString
public class OpenAccountResponse {
    String result;
    AccountResponseHeader Header;
    String bankCode;
    String accountNo;
}
