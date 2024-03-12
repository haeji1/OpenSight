package org.example.b104.domain.user.service.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserCommand {
    private String email;
    private String password;
    private String username;
    private String phone;
}
