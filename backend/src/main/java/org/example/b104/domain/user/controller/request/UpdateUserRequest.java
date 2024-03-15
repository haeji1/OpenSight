package org.example.b104.domain.user.controller.request;

import lombok.Data;
import org.example.b104.domain.user.service.command.UpdateUserCommand;

@Data
public class UpdateUserRequest {
    private String email;
    private String username;
    private String password;

    public UpdateUserCommand toUpdateUserCommand() {
       return UpdateUserCommand.builder()
                .email(getEmail())
                .username(getUsername())
                .password(getPassword())
                .build();
    }
}
