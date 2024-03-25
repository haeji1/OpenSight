package org.example.b104.domain.account.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class InquireDepositorAccountNumberRequest {
    AccountRequestHeader Header;
    String BankCode;
    String AccountNo;
}
