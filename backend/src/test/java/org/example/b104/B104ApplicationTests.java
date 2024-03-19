package org.example.b104;


import org.example.b104.domain.account.controller.response.*;
import org.example.b104.domain.account.service.AccountService;
import org.example.b104.domain.amazon.service.AmazonRekognitionService;
import org.example.b104.domain.amazon.service.AmazonS3Service;
import org.example.b104.domain.amazon.service.AmazonTranscribeService;
//import org.example.b104.oauth2.JwtTokenProvider;
import org.example.b104.domain.openai.service.ChatGptService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.services.rekognition.model.FaceMatch;

import java.util.List;

@SpringBootTest
class B104ApplicationTests {
    @Autowired
    AmazonRekognitionService rekognitionService;
    @Autowired
    AmazonS3Service s3Service;
    @Autowired
    AmazonTranscribeService transcribeService;
    //@Autowired
    //JwtTokenProvider jwtTokenProvider;

    @Autowired
    ChatGptService chatGptService;

    @Autowired
    AccountService accountService;

    @Test
    void contextLoads() {
        System.out.println("#######################################contextLoads");

        /**
         *
         * 1. detectFaces를 통해 얼굴인지 확인하고
         * 2. registerUser를 통해 얼굴을 등록하고
         * 3. recognizeFace를 통해 등록된 얼굴과 비교한다.
         *
         * 이제 해야할일은 S3에 이미지 업로드 하는 부분 해야징
         *
         */

        //String res = rekognitionService.createCollection("cloud-open-sight-collection");
        //aws:rekognition:us-east-1:211125547161:collection/cloud-open-sight-collection
        //System.out.println(res);
        //rekognitionService.detectFaces("c:/mypic.png");
        //String res =rekognitionService.registerUser("cloud-open-sight-ue1","mypic.png");
        //comparethis.jpg
        //lee-pillip.jpg
        //bc655131-7231-4acb-929c-f03ac68ef32d -> 나(류진호)
        //List<FaceMatch> res = rekognitionService.recognizeFace("cloud-open-sight-collection","cloud-open-sight-ue1","comparethis.jpg");

        //String res = s3Service.uploadFile("C:/uploadthis.png");
//        transcribeService.transcribeAudioFile("testvoice.mp3");

//        String res = s3Service.downloadJsonFile("MyTranscriptionJob.json");

        //eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIzIiwiaWF0IjoxNzA5NzE0NTQ5LCJleHAiOjE3MDk3MTQ1NTl9.ieQ93RUpihsOOGpcQE8KCzWY2-r-e2umucAeVd-zlqU
        //String res = jwtTokenProvider.getPayload("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIzIiwiaWF0IjoxNzA5NzE0NTQ5LCJleHAiOjE3MDk3MTQ1NTl9.ieQ93RUpihsOOGpcQE8KCzWY2-r-e2umucAeVd-zlqU");

        //String res = chatGptService.chat("[계좌이체 열어줘.] 괄호안에 있는 내용이 계좌이체면 1, 계좌조회면 2, 이체내역조회면 3, 이체한도조회면 4, 이체한도변경면 5 반환해줘. 다른거 입력하지 말고 숫자만");
        //System.out.println("######## res : " + res);

//        RegisterAccountMemberResponse registerAccountMemberResponse = accountService.registerAccountMember("010218e760dd476db9bac3b474847947",
//                "JinhoRyu.Dev6@gmail.com");
//        System.out.println("######## registerAccountMemberResponse : " + registerAccountMemberResponse);

//
//        SearchAccountMemberResponse searchAccountMemberResponse = accountService.searchAccountMember("010218e760dd476db9bac3b474847947",
//                "JinhoRyu.Dev@gmail.com");
//        System.out.println("######## searchAccountMemberResponse : " + searchAccountMemberResponse);

        //InquireBankAccountTypesResponse inquireBankAccountTypesResponse = accountService.inquireBankAccountTypes("010218e760dd476db9bac3b474847947");
        //System.out.println(inquireBankAccountTypesResponse);

        //내정보
        //SearchAccountMemberResponse(result=succeed, userId=JinhoRyu.Dev@gmail.com, userName=JinhoRyu.Dev, institutionCode=00100, userKey=9d4b8b95-6fbc-421e-aca8-35748bb4b12b, created=2024-03-17T20:58:10.155517+09:00, modified=2024-03-17T20:58:10.155514+09:00, now=2024-03-18T17:03:55.256289+09:00)

        //OpenAccountResponse openAccountResponse = accountService.openAccount("010218e760dd476db9bac3b474847947", "001-1-81fe2deafd1943", "9d4b8b95-6fbc-421e-aca8-35748bb4b12b");
        //System.out.println(openAccountResponse);



        /*
        OpenAccountResponse(result=success, Header=AccountResponseHeader(responseCode=H0000, responseMessage=정상처리 되었습니다., apiName=openAccount, transmissionDate=20240318, transmissionTime=170835, institutionCode=00100, apiKey=010218e760dd476db9bac3b474847947, apiServiceCode=openAccount, institutionTransactionUniqueNo=20240318170835507602), bankCode=001, accountNo=0011964510743365)
         */

//        InquireAccountListResponse inquireAccountListResponse = accountService.inquireAccountList("010218e760dd476db9bac3b474847947", "9d4b8b95-6fbc-421e-aca8-35748bb4b12b");
//        System.out.println(inquireAccountListResponse);
//
//        InquireAccountInfoResponse inquireAccountInfoResponse = accountService.inquireAccountInfo("010218e760dd476db9bac3b474847947", "001","0011964510743365", "9d4b8b95-6fbc-421e-aca8-35748bb4b12b");
//        System.out.println(inquireAccountInfoResponse);

        InquireAccountBalanceResponse inquireAccountBalanceResponse = accountService.inquireAccountBalance("010218e760dd476db9bac3b474847947", "001","0011964510743365", "9d4b8b95-6fbc-421e-aca8-35748bb4b12b");
        System.out.println(inquireAccountBalanceResponse);
    }
}
