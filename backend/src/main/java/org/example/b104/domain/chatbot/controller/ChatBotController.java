package org.example.b104.domain.chatbot.controller;

import lombok.RequiredArgsConstructor;
import org.example.b104.domain.account.controller.response.AccountTransferResponse;
import org.example.b104.domain.chatbot.controller.request.ChatBotTextRequest;
import org.example.b104.domain.chatbot.controller.response.AccountTransferChatBotResponse;
import org.example.b104.domain.chatbot.controller.response.BalanceInquiryResponse;
import org.example.b104.domain.chatbot.service.ChatBotService;
import org.example.b104.global.response.ApiResponse;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/chatbot")
@RequiredArgsConstructor
public class ChatBotController {

    private final ChatBotService chatBotService;


    @PostMapping("/request")
    public ResponseEntity<ApiResponse<?>> receiveTextRequest(
            @RequestHeader("Authorization") String token,
            @RequestBody ChatBotTextRequest request
    ) {
        System.out.println("=========시작=========");
        System.out.println("======토큰=======" + token);
        System.out.println("==========request======" + request);
        if (request.getCommand_id() == 1) {
            AccountTransferChatBotResponse accountTransferChatBotResponse = chatBotService.accountTransfer(token, request.toChatBotTextCommand());
            return ResponseEntity.ok(ApiResponse.createSuccess(accountTransferChatBotResponse));
//        } else if (request.getCommand_id() == 2) {
//            BalanceInquiryResponse balanceInquiryResponse = chatBotService
//        }
//        String chatBotResponse = chatBotService.ReceiveTextRequest(token, request.toChatBotTextCommand());
//        return ResponseEntity.ok(ApiResponse.createSuccess(chatBotResponse));
        }

//    @PostMapping("/history")
//    public ResponseEntity<ApiResponse<String>> receiveRequest(
//            @RequestHeader("Authorization") String token,
//            @RequestBody ChatBotTextRequest request
//    ) {
//        System.out.println("=========시작=========");
//        System.out.println("======토큰======="+token);
//        System.out.println("==========request======"+request);
//        String chatBotResponse = chatBotService.ReceiveTextRequest(token, request.toChatBotTextCommand());
//        return ResponseEntity.ok(ApiResponse.createSuccess(chatBotResponse));
//    }
        return null;
    }

}
