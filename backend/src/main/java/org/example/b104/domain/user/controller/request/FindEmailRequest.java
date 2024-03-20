package org.example.b104.domain.user.controller.request;

import lombok.Data;
import org.example.b104.domain.user.service.command.FindEmailCommand;

@Data
public class FindEmailRequest {
    private String phone;

    public FindEmailCommand toFindEmail() {
        FindEmailCommand command = FindEmailCommand.builder()
                .phone(getPhone())
                .build();
        return command;
    }
}
