package org.example.b104.domain.user.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.example.b104.domain.user.service.command.RegisterSimplePasswordCommand;

@Data
public class RegisterSimplePasswordRequest {
    private String simplePassword;
    public RegisterSimplePasswordCommand toRegisterSimplePasswordCommand() {
        return RegisterSimplePasswordCommand.builder()
                .simplePassword(simplePassword)
                .build();
    }
}
