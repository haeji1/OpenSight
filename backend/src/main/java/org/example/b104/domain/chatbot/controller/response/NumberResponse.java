package org.example.b104.domain.chatbot.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class NumberResponse {
    @JsonProperty("commandId")
    private String commandId;
}
