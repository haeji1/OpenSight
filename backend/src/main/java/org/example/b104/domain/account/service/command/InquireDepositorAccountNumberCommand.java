package org.example.b104.domain.account.service.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class InquireDepositorAccountNumberCommand {
    String userKey;
    String bankCode;
    String accountNo;

}
