package org.example.b104.domain.chatbot.controller;

import lombok.RequiredArgsConstructor;
import org.example.b104.domain.chatbot.controller.request.ChatBotTextRequest;
import org.example.b104.domain.chatbot.controller.response.ChatBotTextResponse;
import org.example.b104.domain.chatbot.service.ChatBotService;
import org.example.b104.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/chatbot")
@RequiredArgsConstructor
public class ChatBotController {

    private final ChatBotService chatBotService;

//    @PostMapping("/request")
//    public ResponseEntity<ApiResponse<ChatBotTextRequest>> receiveTextRequest() {
//        ChatBotTextResponse chatBotTextResponse;
//        return ResponseEntity.ok(ApiResponse.createSuccess(chatBotTextResponse));
//    }
}
