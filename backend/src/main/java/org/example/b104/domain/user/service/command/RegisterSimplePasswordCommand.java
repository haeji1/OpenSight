package org.example.b104.domain.user.service.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterSimplePasswordCommand {
    private String simplePassword;
}
