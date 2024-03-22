package org.example.b104.domain.chatbot.service.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatBotTextCommand {

    private Long number;
    private String tmp;

}
