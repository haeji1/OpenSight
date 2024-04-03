package org.example.b104.domain.chatbot.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.b104.domain.chatbot.service.command.ChatBotIsPositiveAnswerCommand;

@Data
public class ChatBotIsPositiveAnswerRequest {
    private String text;

    public ChatBotIsPositiveAnswerCommand toChatBotIsPositiveAnswerCommand() {
        return ChatBotIsPositiveAnswerCommand.builder()
                .text(text)
                .build();
    }
}
