package org.example.b104.domain.chatbot.controller.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BalanceInquiryResponse {
    private Long command_id;
    private String text1;
    private Long money;
    private String text2;
    private Long ischatbot;
}
