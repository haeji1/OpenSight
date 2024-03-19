package org.example.b104.domain.user.controller.request;

import lombok.Data;
import org.example.b104.domain.user.service.command.UpdatePasswordCommand;

@Data
public class UpdatePasswordRequest {
    private String password;

    public UpdatePasswordCommand toUpdatePasswordCommand() {
        return UpdatePasswordCommand.builder()
                .password(getPassword())
                .build();
    }
}
