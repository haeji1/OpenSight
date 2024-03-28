package org.example.b104.domain.chatbot.controller.request;

import lombok.Data;
import org.example.b104.domain.chatbot.service.command.ChatBotTextCommand;

@Data
public class ChatBotTextRequest {

    private Long command_id;
    private String message;
    private String bank;
    private String account;
    private Long money;

    public ChatBotTextCommand toChatBotTextCommand() {
        return ChatBotTextCommand.builder()
                .command_id(command_id)
                .message(message)
                .bank(bank)
                .account(account)
                .money(money)
                .build();
    }
}
