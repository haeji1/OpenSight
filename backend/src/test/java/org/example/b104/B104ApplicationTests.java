package org.example.b104;


import org.example.b104.domain.amazon.service.AmazonRekognitionService;
import org.example.b104.domain.amazon.service.AmazonS3Service;
import org.example.b104.domain.amazon.service.AmazonTranscribeService;
import org.example.b104.oauth2.JwtTokenProvider;
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

        String res = chatGptService.chat("Hello, how are you?");
        System.out.println("######## res : " + res);



    }

}
