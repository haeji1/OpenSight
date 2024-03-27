package org.example.b104.domain.chatbot.controller;

import lombok.RequiredArgsConstructor;
import org.example.b104.domain.chatbot.controller.request.ChatBotTextRequest;
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
    public ResponseEntity<ApiResponse<JSONObject>> receiveTextRequest(
            @RequestHeader("Authorization") String token,
            @RequestBody ChatBotTextRequest request
    ) {
        JSONObject chatBotResponse = chatBotService.ReceiveTextRequest(token, request.toChatBotTextCommand());
        return ResponseEntity.ok(ApiResponse.createSuccess(chatBotResponse));
    }
}
