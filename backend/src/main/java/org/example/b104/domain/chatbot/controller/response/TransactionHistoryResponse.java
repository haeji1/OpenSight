package org.example.b104.domain.chatbot.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import org.example.b104.domain.account.controller.record.SingleAccountTransactionHistory;

import java.util.ArrayList;

@Getter
@Builder
public class TransactionHistoryResponse {
    @JsonProperty("command_id")
    private Integer command_id;
    @JsonProperty("len")
    private Integer len;
    @JsonProperty("text1")
    private String text1;
    @JsonProperty("history")
    private SingleAccountTransactionHistory[]  history;
    @JsonProperty("text2")
    private String text2;
    @JsonProperty("ischatbot")
    private Integer ischarbot;
}
