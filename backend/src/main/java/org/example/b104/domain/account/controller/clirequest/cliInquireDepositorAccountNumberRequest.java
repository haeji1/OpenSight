package org.example.b104.domain.account.controller.clirequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class cliInquireDepositorAccountNumberRequest {
    private String bankCode;
    private String accountNo;
}
