package org.example.b104.domain.user.controller.request;

import lombok.Data;
import org.example.b104.domain.user.service.command.CreateUserCommand;
import org.springframework.web.multipart.MultipartFile;


@Data
public class CreateUserImageRequest {
    private String email;
    private String password;
    private String username;
    private String phone;

    private MultipartFile profileImage;

    public CreateUserCommand toCreateUserCommand() {
        return CreateUserCommand.builder()
                .email(email)
                .password(password)
                .username(username)
                .phone(phone)
                .build();
    }
}
