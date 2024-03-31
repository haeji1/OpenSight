package org.example.b104.domain.chatbot.service.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatBotTextCommand {

    private Integer command_id;
    private String message;
    private String bank;
    private String account;
    private Integer money;
    private Integer is_chatbot;
}
