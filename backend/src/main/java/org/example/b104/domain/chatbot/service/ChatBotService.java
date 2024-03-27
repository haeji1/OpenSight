package org.example.b104.domain.chatbot.service;

import lombok.RequiredArgsConstructor;
import org.example.b104.domain.account.controller.record.SingleAccountTransactionHistory;
import org.example.b104.domain.account.controller.response.InquireAccountBalanceResponse;
import org.example.b104.domain.account.controller.response.InquireAccountTransactionHistoryResponse;
import org.example.b104.domain.account.service.AccountService;
import org.example.b104.domain.account.service.command.InquireAccountBalanceCommand;
import org.example.b104.domain.account.service.command.InquireAccountHistoryTransactionCommand;
import org.example.b104.domain.chatbot.service.command.ChatBotTextCommand;
import org.example.b104.domain.oauth2.JwtTokenProvider;
import org.example.b104.domain.user.entity.User;
import org.example.b104.domain.user.repository.UserRepository;
import org.example.b104.global.exception.EntityNotFoundException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.awscore.util.SignerOverrideUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatBotService {

    private final AccountService accountService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    /**
     * 1. command 에서 command_id 추출
     * 2. command_id에 따라 로직 처리
     * 3. response 로 알맞는 url 보내주기
     */

    @Transactional(readOnly = true)
    public JSONObject ReceiveTextRequest(String token, ChatBotTextCommand command) {

        Long commandId = command.getCommand_id();
        String userKey = getUserKeyFromToken(token);
        User user = getUserFromToken(token);
        String accountNo = user.getAccountNo();
        String bankCode = user.getBankCode();

        LocalDate currentDate = LocalDate.now();
        String startDate = currentDate.toString();
        LocalDate oneYearLater = currentDate.plusYears(1);
        String endDate = oneYearLater.toString();

        if (commandId == 1) {
        } else if (commandId == 2) {
            InquireAccountBalanceCommand inquireAccountBalanceCommand = InquireAccountBalanceCommand.builder()
                    .bankCode(bankCode)
                    .accountNo(accountNo)
                    .userKey(userKey)
                    .build();
            InquireAccountBalanceResponse inquireAccountBalanceResponse = accountService.inquireAccountBalance(inquireAccountBalanceCommand);
            int balance = inquireAccountBalanceResponse.getREC().accountBalance();
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("text1", "귀하의 잔액은");
            jsonResponse.put("money", balance);
            jsonResponse.put("text2", "원입니다. 다시 듣기를 원하시면…");
            jsonResponse.put("isChatBot",1);
            return jsonResponse;
        } else if (commandId == 3) {
            InquireAccountHistoryTransactionCommand inquireAccountHistoryTransactionCommand = InquireAccountHistoryTransactionCommand.builder()
                    .bankCode(bankCode)
                    .accountNo(accountNo)
                    .startDate(startDate)
                    .endDate(endDate)
                    .transactionType("A")
                    .orderByType("DESC")
                    .build();
            InquireAccountTransactionHistoryResponse inquireAccountTransactionHistoryResponse = accountService.inquireAccountTransactionHistory(inquireAccountHistoryTransactionCommand);
            Integer length = inquireAccountTransactionHistoryResponse.getREC().totalCount();

            JSONObject jsonObject = new JSONObject();
            JSONArray transactionsList = new JSONArray();

            if (length < 5) {
                jsonObject.put("text1", "최근 " + length + "개의 거래내역을 보여드리겠습니다.");
            } else {
                jsonObject.put("text1", "최근 5개의 거래내역을 보여드리겠습니다.");
            }

            SingleAccountTransactionHistory[] list = inquireAccountTransactionHistoryResponse.getREC().transactionHistory();
            System.out.println(list);

//            for (int i = 0; i < Math.min(length, 5); i++) {
//                JSONObject transaction = new JSONObject();
//                JSONObject currentTransaction = list.getJSONObject(i);
//
//                transaction.put("transactionDate", currentTransaction.getString("transactionDate"));
//                transaction.put("transactionSummary", currentTransaction.getString("transactionSummary"));
//                transaction.put("transactionBalance", currentTransaction.getString("transactionBalance"));
//                transaction.put("transactionTypeName", currentTransaction.getString("transactionTypeName"));
//
//                transactionsList.put(transaction);
//            }
//
//            // Add history fields to jsonObject
//            for (int i = 0; i < 5; i++) {
//                if (i < transactionsList.length()) {
//                    jsonObject.put("history" + (i + 1), transactionsList.getJSONObject(i));
//                } else {
//                    jsonObject.put("history" + (i + 1), JSONObject.NULL);
//                }
//            }
//
//            // Add additional fields
//            jsonObject.put("text2", "추가 텍스트");
//            jsonObject.put("ischatbot", 1);
//
//            return jsonObject.toString();
        }
        else if (commandId == 4) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("text1", "비대면계좌개설로 이동할 수 있는 경로는");
            jsonObject.put("url", "/api/accounts/open-account");
            jsonObject.put("text2", "입니다. 잠시만 기다려주세요");
            jsonObject.put("isChatBot",1);
            return jsonObject;
        }
        JSONObject response = new JSONObject();
        response.put("text", "다시 요청해주세요");
        return response;
    }

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

}
