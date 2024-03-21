package org.example.b104.domain.user.controller.request;

import lombok.Data;
import org.example.b104.domain.user.service.command.FindPasswordCommand;

@Data
public class FindPasswordRequest {
    private String phone;
    private String email;

    public FindPasswordCommand toFindPasswordCommand() {
        return FindPasswordCommand.builder()
                .phone(phone)
                .email(email)
                .build();
    }
}
