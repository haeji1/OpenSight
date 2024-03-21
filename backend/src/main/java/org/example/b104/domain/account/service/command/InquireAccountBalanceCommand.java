package org.example.b104.domain.account.service.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class InquireAccountBalanceCommand {
    String bankCode;
    String accountNo;
    String userKey;

}
