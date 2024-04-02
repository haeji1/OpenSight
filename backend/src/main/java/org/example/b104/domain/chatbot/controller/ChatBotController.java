package org.example.b104.domain.chatbot.controller;

import lombok.RequiredArgsConstructor;
import org.example.b104.domain.chatbot.controller.request.ChatBotRawTextRequest;
import org.example.b104.domain.chatbot.controller.request.ChatBotTextRequest;
import org.example.b104.domain.chatbot.controller.response.*;
import org.example.b104.domain.chatbot.service.ChatBotService;
import org.example.b104.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/chatbot")
@RequiredArgsConstructor
public class ChatBotController {

    private final ChatBotService chatBotService;

//    @PostMapping("/request")
//    public ResponseEntity<ApiResponse<?>> receiveTextRequest(
//            @RequestHeader("Authorization") String token,
//            @RequestBody ChatBotTextRequest request
//    ) {
//        System.out.println("=========시작=========");
//        System.out.println("======토큰=======" + token);
//        System.out.println("==========request======" + request);
//        if (request.getCommand_id() == 1) {
//            AccountTransferChatBotResponse accountTransferChatBotResponse = chatBotService.accountTransfer(token, request.toChatBotTextCommand());
//            return ResponseEntity.ok(ApiResponse.createSuccess(accountTransferChatBotResponse));
//        } else if (request.getCommand_id() == 2) {
//            BalanceInquiryResponse balanceInquiryResponse = chatBotService.balanceInquiry(token, request.toChatBotTextCommand());
//            return ResponseEntity.ok(ApiResponse.createSuccess(balanceInquiryResponse));
//        } else if (request.getCommand_id() == 3) {
//            TransactionHistoryResponse transactionHistoryResponse = chatBotService.transactionHistory(token, request.toChatBotTextCommand());
//            return ResponseEntity.ok(ApiResponse.createSuccess(transactionHistoryResponse));
//
//        } else if (request.getCommand_id() == 4) {
//            OpenAccountResponse openAccountResponse = chatBotService.openAccount(request.toChatBotTextCommand());
//            return ResponseEntity.ok(ApiResponse.createSuccess(openAccountResponse));
//        } else {
//            NotValidChatBotResponse notValidChatBotResponse = chatBotService.notValidChatBot(request.toChatBotTextCommand());
//            return ResponseEntity.ok(ApiResponse.createSuccess(notValidChatBotResponse));
//        }
//
//    }

    @PostMapping("/gpt/request")
    public ResponseEntity<ApiResponse<?>> receiveTextRequest(
//            @RequestHeader("Authorization") String token,
            @RequestBody ChatBotRawTextRequest request
            ) {
        System.out.println("=========시작=========");
//        System.out.println("======토큰=======" + token);
        System.out.println("==========request======" + request);
        // gpt 한테 음성 해독해서 처리할 번호 알아내고
        int result = chatBotService.handleUserCommand(request.getText());
        // 번호에 따라 분기처리
        if (result > 0 && result < 5) {
            NumberResponse numberResponse = NumberResponse.builder()
                    .commandId(Integer.toString(result))
                    .build();
//            return ResponseEntity.ok(ApiResponse.createSuccess(accountTransferChatBotResponse));
        return ResponseEntity.ok(ApiResponse.createSuccess(numberResponse));
        } else {
            NumberResponse numberResponse = NumberResponse.builder()
                    .commandId(Integer.toString(-1))
                    .build();;
            return ResponseEntity.ok(ApiResponse.createSuccess(numberResponse));
        }
    }
}
