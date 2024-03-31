package org.example.b104.domain.chatbot.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotValidChatBotResponse {

    private Integer command_id;
    private String result;
    private Integer is_chatbot;
}
