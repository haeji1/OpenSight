package org.example.b104.domain.chatbot.service;

import lombok.RequiredArgsConstructor;
import org.example.b104.domain.account.controller.record.SingleAccountTransactionHistory;
import org.example.b104.domain.account.controller.response.AccountTransferResponse;
import org.example.b104.domain.account.controller.response.InquireAccountBalanceResponse;
import org.example.b104.domain.account.controller.response.InquireAccountTransactionHistoryResponse;
import org.example.b104.domain.account.service.AccountService;
import org.example.b104.domain.account.service.command.AccountTransferCommand;
import org.example.b104.domain.account.service.command.InquireAccountBalanceCommand;
import org.example.b104.domain.account.service.command.InquireAccountHistoryTransactionCommand;
import org.example.b104.domain.chatbot.controller.request.ChatBotTextRequest;
import org.example.b104.domain.chatbot.controller.response.*;
import org.example.b104.domain.chatbot.service.command.ChatBotTextCommand;
import org.example.b104.domain.oauth2.JwtTokenProvider;
import org.example.b104.domain.openai.service.ChatGptService;
import org.example.b104.domain.user.entity.User;
import org.example.b104.domain.user.repository.UserRepository;
import org.example.b104.global.exception.EntityNotFoundException;
import org.example.b104.global.response.ApiResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.time.LocalDate;
import java.util.Arrays;


@Service
@RequiredArgsConstructor
public class ChatBotService {

    private final AccountService accountService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final ChatGptService chatGptService;
    /**
     * 1. command 에서 command_id 추출
     * 2. command_id에 따라 로직 처리
     * 3. response 로 알맞는 url 보내주기
     */

//    @Transactional(readOnly = true)
//    public String ReceiveTextRequest(String token, ChatBotTextCommand command) {
//
//        System.out.println("======시작======"+token);
//        System.out.println("======커맨드===="+command.getCommand_id());
//        String bankCode = "";
//        String bankName = "";
//        if (command.getBank() != null) {
//            bankName = command.getBank();
//        } else {
//            System.out.println("null");
//        }
//        // 은행 이름 분류
//        if (bankName.equals("한국은행"))  {
//            bankCode = "001";
//        } else if (bankName.equals("산업은행")) {
//            bankCode = "002";
//        } else if (bankName.equals("기업은행")) {
//            bankCode = "003";
//        } else if (bankName.equals("국민은행")) {
//            bankCode = "004";
//        }
//
//        Integer commandId = command.getCommand_id();
//        String userKey = getUserKeyFromToken(token);
//        User user = getUserFromToken(token);
//        String accountNo = command.getAccount();
//
//
//        LocalDate currentDate = LocalDate.now();
//        String startDate = currentDate.toString();
//        LocalDate oneYearLater = currentDate.plusYears(1);
//        String endDate = oneYearLater.toString();
//
//        if (commandId == 1) {
//
//            // 빈 정보가 없는지 확인
//            // 계좌이체 정보 맞는지 확인
//            boolean isValidAccountTransfer = checkIfAccountTransferIsCorrect(command, user, bankCode, accountNo, userKey);
//
//            System.out.println("++++user"+user);
//            System.out.println("+++bankCode"+bankCode);
//            System.out.println("+++acountNo"+accountNo);
//            System.out.println("+++userKey"+userKey);
//
//            if (isValidAccountTransfer) {
//
//                // 서비스에 보내기 위한 command
//                AccountTransferCommand accountTransferCommand = AccountTransferCommand.builder()
//                        .depositBankCode(user.getBankCode())
//                        .depositAccountNo(user.getAccountNo())
//                        .transactionBalance(command.getMoney())
//                        .withdrawalBankCode(bankCode)
//                        .withdrawalAccountNo(accountNo)
//                        .depositTransactionSummary("입금이체 계좌")
//                        .withdrawalTransactionSummary("출금이체 계좌")
//                        .userKey(userKey)
//                        .build();
//
//
//
//                // 계좌이체 정보 맞는지 확인
////                JSONObject jsonResponse = new JSONObject();
////                jsonResponse.put("text1","계좌이체를 시작합니다.");
////                jsonResponse.put("bank",command.getBank());
////                jsonResponse.put("account",accountTransferCommand.getWithdrawalAccountNo());
////                jsonResponse.put("money", accountTransferCommand.getTransactionBalance());
////                jsonResponse.put("text2", "계좌이체 하려는 정보가 맞는지 확인해주세요");
////                jsonResponse.put("isChatBot",1);
//
//                // 여기서 다시 예 아니오 요청 받아서 처리
//
//                // 계좌 이체 수행
//                accountService.accountTransfer(accountTransferCommand);
//                JSONObject jsonResponse = new JSONObject();
//                jsonResponse.put("text", "완료되었습니다.");
//                return jsonResponse.toString();
//
//            }
//
//            else {
//                // 계좌이체 정보가 틀린 경우
//                JSONObject jsonResponse = new JSONObject();
//                jsonResponse.put("text1", "다시 요청해주세요");
//                return jsonResponse.toString();
//            }
//
//
//
//            // 분기처리
////             if 맞으면 accountService.accountTransfer(accountTransferCommand);
//            // else
//            /**
//             * JSONObject jsonResponse = new JSONObject();
//             jsonResponse.put("","다시 요청해주세요");
//             return jsonResponse;
//             *
//             */
//        } else if (commandId == 2) {
//            System.out.println("======2번 시작========");
//            InquireAccountBalanceCommand inquireAccountBalanceCommand = InquireAccountBalanceCommand.builder()
//                    .bankCode(bankCode)
//                    .accountNo(accountNo)
//                    .userKey(userKey)
//                    .build();
//            System.out.println("======command 완료=========");
//            InquireAccountBalanceResponse inquireAccountBalanceResponse = accountService.inquireAccountBalance(inquireAccountBalanceCommand);
//            int balance = inquireAccountBalanceResponse.getREC().accountBalance();
//            JSONObject jsonResponse = new JSONObject();
//            jsonResponse.put("text1", "귀하의 잔액은");
//            jsonResponse.put("money", balance);
//            jsonResponse.put("text2", "원입니다. 다시 듣기를 원하시면…");
//            jsonResponse.put("isChatBot",1);
//            return jsonResponse.toString();
//        } else if (commandId == 3) {
//            System.out.println("+++userKey"+userKey);
//            InquireAccountHistoryTransactionCommand inquireAccountHistoryTransactionCommand = InquireAccountHistoryTransactionCommand.builder()
//                    .bankCode(bankCode)
//                    .accountNo(accountNo)
//                    .startDate(startDate)
//                    .endDate(endDate)
//                    .transactionType("A")
//                    .orderByType("DESC")
//                    .build();
//            System.out.println("========테스트======="+commandId);
//            System.out.println("message"+command.getMessage());
//            System.out.println("ischatbot"+command.getIs_chatbot());
//            InquireAccountTransactionHistoryResponse inquireAccountTransactionHistoryResponse = accountService.inquireAccountTransactionHistory(inquireAccountHistoryTransactionCommand);
//            Integer length = inquireAccountTransactionHistoryResponse.getREC().totalCount();
//
//            JSONObject jsonObject = new JSONObject();
//            JSONArray transactionsList = new JSONArray();
//
//            jsonObject.put("command_id",commandId);
//            jsonObject.put("len",length);
//            if (length < 5) {
//                jsonObject.put("text1", "최근 " + length + "개의 거래내역을 보여드리겠습니다.");
//            } else {
//                jsonObject.put("text1", "최근 5개의 거래내역을 보여드리겠습니다.");
//            }
//
//            SingleAccountTransactionHistory[] list = inquireAccountTransactionHistoryResponse.getREC().transactionHistory();
////            System.out.println(list);
//
//            for (int i = 0; i < Math.min(length, 5); i++) {
//                SingleAccountTransactionHistory currentTransaction = list[i];
//
//                JSONObject transaction = new JSONObject();
//                transaction.put("transactionDate", currentTransaction.transactionDate());
//                transaction.put("transactionSummary", currentTransaction.transactionSummary());
//                transaction.put("transactionBalance", currentTransaction.transactionBalance());
//                transaction.put("transactionTypeName", currentTransaction.transactionTypeName());
//
//                transactionsList.put(transaction);
//            }
//
//            for (int i = 0; i < 5; i++) {
//                if (i < transactionsList.length()) {
//                    jsonObject.put("history" + (i + 1), transactionsList.getJSONObject(i));
//                } else {
//                    jsonObject.put("history" + (i + 1), JSONObject.NULL);
//                }
//            }
//            jsonObject.put("text2", "추가 텍스트");
//            jsonObject.put("isChatBot",1);
//            return jsonObject.toString();
//        }
//        else if (commandId == 4) {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("command_id",commandId);
//            jsonObject.put("text1", "비대면계좌개설로 이동할 수 있는 경로는");
//            jsonObject.put("url", "/api/accounts/open-account");
//            jsonObject.put("text2", "입니다. 잠시만 기다려주세요");
//            jsonObject.put("isChatBot",1);
//            return jsonObject.toString();
//        }
//        JSONObject response = new JSONObject();
//        response.put("text", "다시 요청해주세요");
//        return response.toString();
//    }

    public Integer handleUserCommand(String text) {
        // 1. text 프롬포트에 넣기
//        "[사과, 딸기, 포도]" +
//                "이 대 괄호 안에 있는 상품들 중에\n" +
//                "[1. (출금) 사과 5,000 \n 2. (출금) 딸기 10,000 \n 3. (출금) 바나나 4,000\n 4. (출금) 사과 6,000]\n" +
//                "이 대 괄호 안에 있는 내용을 참조해서 내가 가장 좋아하는 상품을 추천해줘.\n" +
//                "답변은 다른말 하지말고 상품 숫자 하나만 대답해줘.\n"+
//                "예를들어 사과면 '2'");

        String promptedMessage = "[]안에 있는 문장에 대한 답변을 다음과 같이 해줘.\n" +
                "계좌이체와 관련되어 있으면 '1',\n " +
                "잔액조회와 관련되어 있으면 '2',\n " +
                "거래내역조회는 '3',\n " +
                "비대면 계좌개설은 '4'로 응답해줘.\n"+
                "답변은 다른말 하지말고 상품 숫자 하나만 대답해줘.\n"+
                "예를들어 계좌이체와 관련있으면 너는 \n" +
                "'1'\n" +
                "이라고 대답해\n"+
                "["+text+"]";;

//        System.out.println(promptedMessage);

        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;
        int maxFrequency = 0;
        int mostFrequentNumber = 0;

        for(int i = 0; i < 5; i++) {
            String result = chatGptService.chat(promptedMessage);
            Integer number = Integer.parseInt(result);

            if (number == 1) {
                a++;
                if (a > maxFrequency) {
                    maxFrequency = a;
                    System.out.println("a"+a);
                    mostFrequentNumber = 1;
                }
            } else if (number == 2) {
                b++;
                if (b > maxFrequency) {
                    maxFrequency = b;
                    System.out.println("b"+b);
                    mostFrequentNumber = 2;
                }
            } else if (number == 3) {
                c++;
                if (c > maxFrequency) {
                    maxFrequency = c;
                    System.out.println("c"+c);
                    mostFrequentNumber = 3;
                }
            } else if (number == 4) {
                d++;
                if (d > maxFrequency) {
                    maxFrequency = d;
                    System.out.println("d"+d);
                    mostFrequentNumber = 4;
                }
            } else {
                continue;
            }
        }

        System.out.println(mostFrequentNumber);
        return mostFrequentNumber;
//        if(mostFrequentNumber == 1) {
//            // 1. gpt 응답으로 아래 세개 정보 입력받기
//            // 2. 금액, 계좌번호, 은행코드 입력받기
//            // 3. chatBotTextCommand 값 넣기
//            // 4. 서비스 수행
//
//        } else if (mostFrequentNumber == 2) {
//            ChatBotTextCommand chatBotTextCommand = ChatBotTextCommand.builder()
//                    .command_id(mostFrequentNumber)
//                    .message()
//                    .bank()
//                    .account()
//                    .money()
//                    .is_chatbot(1)
//                    .build();
//            balanceInquiry(token, chatBotTextCommand);
//        } else if (mostFrequentNumber == 3) {
//
//        } else if (mostFrequentNumber == 4) {
//
//        }
    }


    public AccountTransferChatBotResponse accountTransfer(String token, ChatBotTextCommand command) {
        System.out.println("======시작======" + token);
        System.out.println("======커맨드====" + command.getCommand_id());
        String bankCode = "";
        String bankName = "";
        if (command.getBank() != null) {
            bankName = command.getBank();
        } else {
            System.out.println("null");
        }
        // 은행 이름 분류
        if (bankName.equals("한국은행")) {
            bankCode = "001";
        } else if (bankName.equals("산업은행")) {
            bankCode = "002";
        } else if (bankName.equals("기업은행")) {
            bankCode = "003";
        } else if (bankName.equals("국민은행")) {
            bankCode = "004";
        }

//        Integer commandId = command.getCommand_id();
        String userKey = getUserKeyFromToken(token);
        User user = getUserFromToken(token);
        String accountNo = command.getAccount();


        LocalDate currentDate = LocalDate.now();
        String startDate = currentDate.toString();
        LocalDate oneYearLater = currentDate.plusYears(1);
        String endDate = oneYearLater.toString();

        // 빈 정보가 없는지 확인
        // 계좌이체 정보 맞는지 확인
        boolean isValidAccountTransfer = checkIfAccountTransferIsCorrect(command, user, bankCode, accountNo, userKey);

        System.out.println("++++user" + user);
        System.out.println("+++bankCode" + bankCode);
        System.out.println("+++acountNo" + accountNo);
        System.out.println("+++userKey" + userKey);

        if (isValidAccountTransfer) {

            // 서비스에 보내기 위한 command
            AccountTransferCommand accountTransferCommand = AccountTransferCommand.builder()
                    .depositBankCode(user.getBankCode())
                    .depositAccountNo(user.getAccountNo())
                    .transactionBalance(command.getMoney())
                    .withdrawalBankCode(bankCode)
                    .withdrawalAccountNo(accountNo)
                    .depositTransactionSummary("입금이체 계좌")
                    .withdrawalTransactionSummary("출금이체 계좌")
                    .userKey(userKey)
                    .build();


            // 계좌 이체 수행
            accountService.accountTransfer(accountTransferCommand);
            AccountTransferChatBotResponse accountTransferChatBotResponse = AccountTransferChatBotResponse.builder()
                    .text("완료되었습니다.")
                    .build();
            return accountTransferChatBotResponse;
        }

        else {
            // 계좌이체 정보가 틀린 경우
            AccountTransferChatBotResponse accountTransferChatBotResponse = AccountTransferChatBotResponse.builder()
                    .text("다시 요청해주세요.")
                    .build();
            return accountTransferChatBotResponse;
        }


    }

    public BalanceInquiryResponse balanceInquiry(String token, ChatBotTextCommand command) {

        String bankCode = "";
        String bankName = "";
        if (command.getBank() != null) {
            bankName = command.getBank();
        } else {
            System.out.println("null");
        }
        // 은행 이름 분류
        if (bankName.equals("한국은행")) {
            bankCode = "001";
        } else if (bankName.equals("산업은행")) {
            bankCode = "002";
        } else if (bankName.equals("기업은행")) {
            bankCode = "003";
        } else if (bankName.equals("국민은행")) {
            bankCode = "004";
        }

        Integer commandId = command.getCommand_id();
        String userKey = getUserKeyFromToken(token);
        User user = getUserFromToken(token);
        String accountNo = command.getAccount();


        LocalDate currentDate = LocalDate.now();
        String startDate = currentDate.toString();
        LocalDate oneYearLater = currentDate.plusYears(1);
        String endDate = oneYearLater.toString();
        System.out.println("======2번 시작========");
        InquireAccountBalanceCommand inquireAccountBalanceCommand = InquireAccountBalanceCommand.builder()
                .bankCode(bankCode)
                .accountNo(accountNo)
                .userKey(userKey)
                .build();
        System.out.println("======command 완료=========");
        InquireAccountBalanceResponse inquireAccountBalanceResponse = accountService.inquireAccountBalance(inquireAccountBalanceCommand);
        int balance = inquireAccountBalanceResponse.getREC().accountBalance();

        BalanceInquiryResponse balanceInquiryResponse = BalanceInquiryResponse.builder()
                .command_id(commandId)
                .text1("귀하의 잔액은")
                .money(balance)
                .text2("원입니다.")
                .ischatbot(1)
                .build();
        return balanceInquiryResponse;
    }

    public TransactionHistoryResponse transactionHistory(String token, ChatBotTextCommand command) {
        System.out.println("======시작======"+token);
        System.out.println("======커맨드===="+command.getCommand_id());
        String bankCode = "";
        String bankName = "";
        if (command.getBank() != null) {
            bankName = command.getBank();
        } else {
            System.out.println("null");
        }
        // 은행 이름 분류
        if (bankName.equals("한국은행"))  {
            bankCode = "001";
        } else if (bankName.equals("산업은행")) {
            bankCode = "002";
        } else if (bankName.equals("기업은행")) {
            bankCode = "003";
        } else if (bankName.equals("국민은행")) {
            bankCode = "004";
        }

        Integer commandId = command.getCommand_id();
        String userKey = getUserKeyFromToken(token);
        User user = getUserFromToken(token);
        String accountNo = command.getAccount();


        LocalDate currentDate = LocalDate.now();
        String startDate = currentDate.toString();
        LocalDate oneYearLater = currentDate.plusYears(1);
        String endDate = oneYearLater.toString();

        InquireAccountHistoryTransactionCommand inquireAccountHistoryTransactionCommand = InquireAccountHistoryTransactionCommand.builder()
                .bankCode(bankCode)
                .accountNo(accountNo)
                .startDate(startDate)
                .endDate(endDate)
                .transactionType("A")
                .orderByType("DESC")
                .userKey(userKey)
                .build();

        InquireAccountTransactionHistoryResponse inquireAccountTransactionHistoryResponse = accountService.inquireAccountTransactionHistory(inquireAccountHistoryTransactionCommand);
        Integer length = inquireAccountTransactionHistoryResponse.getREC().totalCount();
        SingleAccountTransactionHistory[] histories = inquireAccountTransactionHistoryResponse.getREC().transactionHistory();

        SingleAccountTransactionHistory[] trimmedHistories;
        if (histories.length >= 5) {
            // 처음 5개 요소만 선택하여 새로운 배열 생성
            trimmedHistories = Arrays.copyOf(histories, 5);
        } else {
            trimmedHistories = histories;
        }
        TransactionHistoryResponse transactionHistoryResponse = TransactionHistoryResponse.builder()
                .command_id(commandId)
                .len(length)
                .text1("최근"+length+"개의 거래내역을 보여드레깄습니다.")
                .history(trimmedHistories)
                .text2("추가 텍스트")
                .ischarbot(1)
                .build();
        return transactionHistoryResponse;
    }

    public OpenAccountResponse openAccount(ChatBotTextCommand command) {
        Integer commandId = command.getCommand_id();

        OpenAccountResponse openAccountResponse = OpenAccountResponse.builder()
                .command_id(commandId)
                .text1("비대면계좌개설로 이동할 수 있는 경로는")
                .url("/api/accounts/open-accunt")
                .text2("입니다. 잠시만 기다려주세요.")
                .ischatbot(1)
                .build();
        return openAccountResponse;
    }

    public NotValidChatBotResponse notValidChatBot(ChatBotTextCommand command) {
        Integer commandId = command.getCommand_id();
        NotValidChatBotResponse notValidChatBotResponse = NotValidChatBotResponse.builder()
                .command_id(commandId)
                .result("다시 요청해주세요.")
                .is_chatbot(1)
                .build();
        return notValidChatBotResponse;
    }

    private boolean checkIfAccountTransferIsCorrect(ChatBotTextCommand command, User user, String
            bankCode, String accountNo, String userKey) {
        if (command != null && user != null && bankCode != null && !bankCode.isEmpty() &&
                accountNo != null && !accountNo.isEmpty() && userKey != null && !userKey.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    private String getUserKeyFromToken(String token) {
        String userId = jwtTokenProvider.getPayload(token);
        Long userIdLong = Long.parseLong(userId);
        User user = userRepository.findById(userIdLong)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));
        String userKey = user.getUserKey();
        return userKey;
    }

    private User getUserFromToken(String token) {
        String userId = jwtTokenProvider.getPayload(token);
        Long userIdLong = Long.parseLong(userId);
        User user = userRepository.findById(userIdLong)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));
        return user;
    }

}



