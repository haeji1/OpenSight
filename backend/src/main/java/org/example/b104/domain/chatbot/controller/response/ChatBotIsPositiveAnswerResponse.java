package org.example.b104.domain.chatbot.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatBotIsPositiveAnswerResponse {
    @JsonProperty("commandId")
    private String commandId;
    @JsonProperty("answer")
    private String answer;
}
