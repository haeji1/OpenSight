package org.example.b104.domain.chatbot.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.example.b104.domain.chatbot.service.command.ChatBotRawTextCommand;

//@Getter
//@Builder
@Data
public class ChatBotRawTextRequest {
    @JsonProperty("text")
    private String text;

    public ChatBotRawTextCommand toChatBoRawTextCommand() {
        return ChatBotRawTextCommand.builder()
                .text(text)
                .build();
    }
}
