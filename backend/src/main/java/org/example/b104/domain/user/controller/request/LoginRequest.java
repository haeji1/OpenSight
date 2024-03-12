package org.example.b104.domain.user.controller.request;

import lombok.Builder;
import lombok.Data;
import org.example.b104.domain.user.service.command.LoginCommand;

@Data
@Builder
public class LoginRequest {
    String userEmail;
    String userPassword;

    public LoginCommand toLogin() {
        LoginCommand command = LoginCommand.builder()
                .userEmail(getUserEmail())
                .userPassword(getUserPassword())
                .build();
        return command;
    }
}
