package org.example.b104.domain.chatbot.controller.request;

import lombok.Data;
import org.example.b104.domain.chatbot.service.command.ChatBotTextCommand;

@Data
public class ChatBotTextRequest {

    private Long number;
    private String tmp;

    public ChatBotTextCommand toChatBotTextCommand() {
        return ChatBotTextCommand.builder()
                .number(number)
                .tmp(tmp)
                .build();
    }
}
