package org.example.b104.domain.user.controller.request;

import lombok.Data;
import org.example.b104.domain.user.service.command.CreateUserCommand;

@Data
public class CreateUserRequest {
    private String email;
    private String password;
    private String username;

    public CreateUserCommand toCreateUserCommand() {
        return CreateUserCommand.builder()
                .email(email)
                .password(password)
                .username(username)
                .build();
    }
}
