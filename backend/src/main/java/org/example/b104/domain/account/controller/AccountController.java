package org.example.b104.domain.account.controller;

import lombok.RequiredArgsConstructor;
import org.example.b104.domain.account.controller.request.MakeManagerKeyRequest;
import org.example.b104.domain.account.controller.response.MakeManagerKeyResponse;
import org.example.b104.domain.account.service.AccountService;
import org.example.b104.domain.user.service.UserService;
import org.example.b104.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;

    /*
    73becd130acc489fb571bf0e0826f5bd
     */
    @PostMapping("/make-manager-key")
    public ResponseEntity<ApiResponse<MakeManagerKeyResponse>> makeManagerKey(
            @RequestBody MakeManagerKeyRequest request
    ) {
        MakeManagerKeyResponse makeManagerKeyResponse = accountService.makeManagerKey(request.getManagerId());
        if (makeManagerKeyResponse == null) {
            return ResponseEntity.ok(ApiResponse.createError("error"));
        }
        return ResponseEntity.ok(ApiResponse.createSuccess(makeManagerKeyResponse));
    }
    @PostMapping("/remake-manager-key")
    public ResponseEntity<ApiResponse<MakeManagerKeyResponse>> remakeManagerKey(
            @RequestBody MakeManagerKeyRequest request
    ) {
        MakeManagerKeyResponse makeManagerKeyResponse = accountService.remakeManagerKey(request.getManagerId());
        if (makeManagerKeyResponse == null) {
            return ResponseEntity.ok(ApiResponse.createError("error"));
        }
        return ResponseEntity.ok(ApiResponse.createSuccess(makeManagerKeyResponse));
    }
}
