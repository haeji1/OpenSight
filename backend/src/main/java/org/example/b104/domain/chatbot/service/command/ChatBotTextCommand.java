package org.example.b104.domain.chatbot.service.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatBotTextCommand {

    private Long command_id;
    private String message;
    private String bank;
    private String account;
    private Long money;
    private Long is_chatbot;
}
