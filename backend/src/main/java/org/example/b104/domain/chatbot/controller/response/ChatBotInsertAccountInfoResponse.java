package org.example.b104.domain.chatbot.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatBotInsertAccountInfoResponse {
    @JsonProperty("commandId")
    private String commandId;
    @JsonProperty("bankCode")
    private String bankCode;
    @JsonProperty("bankName")
    private String bankName;
    @JsonProperty("accountNumber")
    private String accountNumber;
    @JsonProperty("transferBalance")
    private String transferBalance;
}
