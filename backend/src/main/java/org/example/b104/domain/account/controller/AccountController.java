package org.example.b104.domain.account.controller;

import lombok.RequiredArgsConstructor;
import org.example.b104.domain.account.controller.request.MakeManagerKeyRequest;
import org.example.b104.domain.account.controller.request.OpenAccountRequest;
import org.example.b104.domain.account.controller.response.MakeManagerKeyResponse;
import org.example.b104.domain.account.controller.response.SearchAccountMemberResponse;
import org.example.b104.domain.account.service.AccountService;
import org.example.b104.domain.account.service.command.OpenAccountCommand;
import org.example.b104.domain.account.service.command.SearchAccountMemberCommand;
import org.example.b104.domain.oauth2.JwtTokenProvider;
import org.example.b104.domain.user.entity.User;
import org.example.b104.domain.user.repository.UserRepository;
import org.example.b104.domain.user.service.UserService;
import org.example.b104.global.exception.EntityNotFoundException;
import org.example.b104.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;


    private String getUserKeyFromToken(String token) {
        String userId = jwtTokenProvider.getPayload(token);
        Long userIdLong = Long.parseLong(userId);
        User user = userRepository.findById(userIdLong)
                .orElseThrow(()-> new EntityNotFoundException("존재하지 않는 유저입니다."));
        String userKey = user.getUserKey();
        return userKey;
    }

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
    @PostMapping("/member") // 맴버 가입
    public ResponseEntity<ApiResponse<OpenAccountRequest>> OpenMember(
            @RequestHeader("Authorization") String token//,
            //@RequestBody OpenAccountRequest request
    ){
        String userKey = getUserKeyFromToken(token);
        System.out.println("userKey: " + userKey);

        return ResponseEntity.ok(ApiResponse.createSuccess(null));
    }
    @PostMapping("/member/search")
    public ResponseEntity<ApiResponse<SearchAccountMemberResponse>> searchMember(
            @RequestHeader("Authorization") String token
    ){
        String userId = jwtTokenProvider.getPayload(token);
        Long userIdLong = Long.parseLong(userId);
        User user = userRepository.findById(userIdLong)
                .orElseThrow(()-> new EntityNotFoundException("존재하지 않는 유저입니다."));
        System.out.println(user.getEmail());

        return ResponseEntity.ok(ApiResponse.createSuccess(accountService.searchAccountMember(
                SearchAccountMemberCommand.builder()
                        .userId(user.getEmail())
                        .build())));
    }


}
