package org.example.b104.domain.user.controller.request;

import lombok.Builder;
import lombok.Data;
import org.example.b104.domain.user.service.command.LoginCommand;

@Data
@Builder
public class LoginRequest {
    private String userEmail;
    private String userPassword;

    public LoginCommand toLogin() {
        LoginCommand command = LoginCommand.builder()
                .userEmail(getUserEmail())
                .userPassword(getUserPassword())
                .build();
        return command;
    }
}
