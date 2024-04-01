package org.example.b104.domain.user.controller.request;

import lombok.Data;
import org.example.b104.domain.user.service.command.ConfirmSimplePasswordCommand;

@Data
public class ConfirmSimplePasswordRequest {
    private String simplePassword;

    public ConfirmSimplePasswordCommand toConfirmSimplePasswordCommand() {
        return ConfirmSimplePasswordCommand.builder()
                .simplePassword(simplePassword)
                .build();
    }
}
