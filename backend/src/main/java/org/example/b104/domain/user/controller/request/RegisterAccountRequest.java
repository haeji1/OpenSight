package org.example.b104.domain.user.controller.request;

import lombok.Builder;
import lombok.Data;
import org.example.b104.domain.user.service.command.RegisterAccountCommand;

@Data
@Builder
public class RegisterAccountRequest {
    private String accountNo;
    private String bankCode;

    public RegisterAccountCommand toRegisterAccountCommand() {
        return RegisterAccountCommand.builder()
                .accountNo(accountNo)
                .bankCode(bankCode)
                .build();
    }
}
