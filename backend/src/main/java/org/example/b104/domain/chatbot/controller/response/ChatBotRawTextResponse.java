package org.example.b104.domain.chatbot.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class ChatBotRawTextResponse {
    @JsonProperty("command_id")
    private Integer command_id;
    private String message;
    private String bank;
    private String account;
    private Integer money;
    @JsonProperty("ischatbot")
    private Integer ischatbot;
}
