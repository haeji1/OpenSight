package org.example.b104.domain.account.controller;

import lombok.RequiredArgsConstructor;
import org.example.b104.domain.account.controller.clirequest.*;
import org.example.b104.domain.account.controller.request.DrawingTransferRequest;
import org.example.b104.domain.account.controller.request.InquireAccountInfoRequest;
import org.example.b104.domain.account.controller.request.MakeManagerKeyRequest;
import org.example.b104.domain.account.controller.request.OpenAccountRequest;
import org.example.b104.domain.account.controller.response.*;
import org.example.b104.domain.account.service.AccountService;
import org.example.b104.domain.account.service.command.*;
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

    private User getUserFromToken(String token) {
        String userId = jwtTokenProvider.getPayload(token);
        Long userIdLong = Long.parseLong(userId);
        User user = userRepository.findById(userIdLong)
                .orElseThrow(()-> new EntityNotFoundException("존재하지 않는 유저입니다."));
        return user;
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
            @RequestHeader("Authorization") String token
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

    @PostMapping("/inquire-bank-account-list-type")
    public ResponseEntity<ApiResponse<InquireBankAccountTypesResponse>> inquireBankAccountList() {
        return ResponseEntity.ok(ApiResponse.createSuccess(accountService.inquireBankAccountTypes()));
    }

    @PostMapping("/open-account")
    public ResponseEntity<ApiResponse<OpenAccountResponse>> openAccount(
            @RequestHeader("Authorization") String token,
            @RequestBody cliOpenAccountRequest request
    ) {
        User user = getUserFromToken(token);
        if (user != null) {
            OpenAccountResponse openAccountResponse = accountService.openAccount(OpenAccountCommand.builder()
                    .accountTypeUniqueNo(request.getAccountTypeUniqueNo())
                    .userKey(getUserKeyFromToken(token))
                    .build());
            return ResponseEntity.ok(ApiResponse.createSuccess(openAccountResponse));
        }
        else {
            return ResponseEntity.ok(ApiResponse.createError("error"));
        }
    }

    @PostMapping("/inquire-account-list")
    public ResponseEntity<ApiResponse<InquireAccountListResponse>> inquireAccountList(
            @RequestHeader("Authorization") String token
    ) {
        User user = getUserFromToken(token);
        if (user != null) {
            System.out.println(user.getUserId());
            System.out.println(user.getUserKey());
            InquireAccountListResponse inquireAccountList = accountService.inquireAccountList(
                    InquireAccountListCommand.builder()
                            .userKey(getUserKeyFromToken(token))
                            .build());
            return ResponseEntity.ok(ApiResponse.createSuccess(inquireAccountList));
        }
        else {
            return ResponseEntity.ok(ApiResponse.createError("error"));
        }
    }

    @PostMapping("/inquire-account-info")
    public ResponseEntity<ApiResponse<InquireAccountInfoResponse>> inquireAccountInfo(
            @RequestHeader("Authorization") String token,
            @RequestBody cliInquireAccountInfoRequest request
    ) {
        User user = getUserFromToken(token);
        if (user != null) {
            InquireAccountInfoResponse inquireAccountInfo = accountService.inquireAccountInfo(
                    InquireAccountInfoCommand.builder()
                            .bankCode(request.getBankCode())
                            .accountNo(request.getAccountNo())
                            .userKey(getUserKeyFromToken(token))
                            .build()
            );
            return ResponseEntity.ok(ApiResponse.createSuccess(inquireAccountInfo));
        }
        else {
            return ResponseEntity.ok(ApiResponse.createError("error"));
        }
    }

    @PostMapping("/inquire-depossitor-account-number")
    public ResponseEntity<ApiResponse<InquireDepositorAccountNumberResponse>> inquireDepositorAccountNumber(
        @RequestHeader("Authorization") String token,
        @RequestBody cliInquireDepositorAccountNumberRequest request
    ){
        User user = getUserFromToken(token);
        if (user != null) {
            InquireDepositorAccountNumberResponse inquireDepositorAccountNumber = accountService.inquireDepositorAccountNumber(
                    InquireDepositorAccountNumberCommand.builder()
                            .bankCode(request.getBankCode())
                            .accountNo(request.getAccountNo())
                            .userKey(getUserKeyFromToken(token))
                            .build()
            );
            return ResponseEntity.ok(ApiResponse.createSuccess(inquireDepositorAccountNumber));
        }
        else {
            return ResponseEntity.ok(ApiResponse.createError("error"));
        }
    }

    @PostMapping("/inquire-account-balance")
    public ResponseEntity<ApiResponse<InquireAccountBalanceResponse>> inquireAccountBalance(
            @RequestHeader("Authorization") String token,
            @RequestBody cliInquireAccountBalanceRequest request
    ) {
        User user = getUserFromToken(token);
        if (user != null) {
            InquireAccountBalanceResponse inquireAccountBalance = accountService.inquireAccountBalance(
                    InquireAccountBalanceCommand.builder()
                            .bankCode(request.getBankCode())
                            .accountNo(request.getAccountNo())
                            .userKey(getUserKeyFromToken(token))
                            .build()
            );
            return ResponseEntity.ok(ApiResponse.createSuccess(inquireAccountBalance));
        }
        else {
            return ResponseEntity.ok(ApiResponse.createError("error"));
        }
    }

    @PostMapping("/drawing-transfer")
    public ResponseEntity<ApiResponse<DrawingTransferResponse>> drawingTransfer(
            @RequestHeader("Authorization") String token,
            @RequestBody cliDrawingTransferRequest request
    ){
        User user = getUserFromToken(token);
        if (user != null) {
            DrawingTransferResponse drawingTransfer = accountService.drawingTransfer(
                    DrawingTransferCommand.builder()
                            .bankCode(request.getBankCode())
                            .accountNo(request.getAccountNo())
                            .transactionBalance(request.getTransactionBalance())
                            .transactionSummary(request.getTransactionSummary())
                            .userKey(getUserKeyFromToken(token))
                            .build()
            );
            return ResponseEntity.ok(ApiResponse.createSuccess(drawingTransfer));
        }
        else {
            return ResponseEntity.ok(ApiResponse.createError("error"));
        }
    }
    @PostMapping("/received-transfer-account-number")
    public ResponseEntity<ApiResponse<ReceivedTransferAccountNumberResponse>> receivedTransferAccountNumberResponse(
            @RequestHeader("Authorization") String token,
            @RequestBody cliReceivedTransferAccountNumberRequest request
    ){
        User user = getUserFromToken(token);
        if (user != null) {
            ReceivedTransferAccountNumberResponse receiveTransferAccountNumber = accountService.receivedTransferAccountNumber(
                    ReceivedTransferAccountNumberCommand.builder()
                            .bankCode(request.getBankCode())
                            .accountNo(request.getAccountNo())
                            .transactionBalance(request.getTransactionBalance())
                            .transactionSummary(request.getTransactionSummary())
                            .userKey(getUserKeyFromToken(token))
                            .build(
            )
            );
            return ResponseEntity.ok(ApiResponse.createSuccess(receiveTransferAccountNumber));
        }
        else {
            return ResponseEntity.ok(ApiResponse.createError("error"));
        }
    }


    @PostMapping("/account-transfer")
    public ResponseEntity<ApiResponse<AccountTransferResponse>> accountTransfer(
            @RequestHeader("Authorization") String token,
            @RequestBody cliAccountTransferRequest request
    ){
        User user = getUserFromToken(token);
        if (user != null) {
            AccountTransferResponse accountTransfer = accountService.accountTransfer(
                    AccountTransferCommand.builder()
                            .depositBankCode(request.getDepositBankCode())
                            .depositAccountNo(request.getDepositAccountNo())
                            .transactionBalance(request.getTransactionBalance())
                            .withdrawalBankCode(request.getWithdrawalBankCode())
                            .withdrawalAccountNo(request.getWithdrawalAccountNo())
                            .depositTransactionSummary(request.getDepositTransactionSummary())
                            .withdrawalTransactionSummary(request.getWithdrawalTransactionSummary())
                            .userKey(getUserKeyFromToken(token))
                            .build()
            );
            return ResponseEntity.ok(ApiResponse.createSuccess(accountTransfer));
        }
        else {
            return ResponseEntity.ok(ApiResponse.createError("error"));
        }
    }

    @PostMapping("/inquire-account-transaction-history")
    public ResponseEntity<ApiResponse<InquireAccountTransactionHistoryResponse>> inquireAccountTransactionHistory(
            @RequestHeader("Authorization") String token,
            @RequestBody cliInquireAccountTransactionHistoryRequest request
    ){
        User user = getUserFromToken(token);
        if (user != null) {
            InquireAccountTransactionHistoryResponse inquireAccountTransactionHistory = accountService.inquireAccountTransactionHistory(
                    InquireAccountHistoryTransactionCommand.builder()
                            .bankCode(request.getBankCode())
                            .accountNo(request.getAccountNo())
                            .userKey(getUserKeyFromToken(token))
                            .startDate(request.getStartDate())
                            .endDate(request.getEndDate())
                            .transactionType(request.getTransactionType())
                            .orderByType(request.getOrderByType())
                            .build()
            );
            return ResponseEntity.ok(ApiResponse.createSuccess(inquireAccountTransactionHistory));
        }
        else {
            return ResponseEntity.ok(ApiResponse.createError("error"));
        }
    }

    @PostMapping("/inquire-transaction-history-detail")
    public ResponseEntity<ApiResponse<InquireTransactionHistoryDetailResponse>> inquireAccountTransactionHistoryDetail(
            @RequestHeader("Authorization") String token,
            @RequestBody cliInquireTransactionHistoryDetailRequest request
    ){
        User user = getUserFromToken(token);
        if (user != null) {
            InquireTransactionHistoryDetailResponse inquireAccountTransactionHistoryDetail = accountService.inquireTransactionHistoryDetail(
                    InquireTransactionHistoryDetailCommand.builder()
                            .bankCode(request.getBankCode())
                            .accountNo(request.getAccountNo())
                            .transactionUniqueNo(request.getTransactionUniqueNo())
                            .userKey(getUserKeyFromToken(token))
                            .build()
            );
            return ResponseEntity.ok(ApiResponse.createSuccess(inquireAccountTransactionHistoryDetail));
        }
        else {
            return ResponseEntity.ok(ApiResponse.createError("error"));
        }
    }
}
