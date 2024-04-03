package org.example.b104.domain.chatbot.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.b104.domain.chatbot.service.command.ChatBotInsertAccountInfoCommand;
import org.example.b104.domain.chatbot.service.command.ChatBotRawTextCommand;

@Data
public class ChatBotInsertAccountInfoRequest {
    @JsonProperty("text")
    private String text;

    public ChatBotInsertAccountInfoCommand toChatBotInsertAccountInfoCommand() {
        return ChatBotInsertAccountInfoCommand.builder()
                .text(text)
                .build();
    }
}
