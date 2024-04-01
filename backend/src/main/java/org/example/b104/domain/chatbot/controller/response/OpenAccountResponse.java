package org.example.b104.domain.chatbot.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OpenAccountResponse {
    private Integer command_id;
    private String text1;
    private String url;
    private String text2;
    private Integer ischatbot;
}
