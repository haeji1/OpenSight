package org.example.b104.domain.chatbot.service.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatBotInsertAccountInfoCommand {
    private String text;
}
