package org.example.b104;

import org.example.b104.global.util.AmazonRekognitionService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class B104ApplicationTests {

    AmazonRekognitionService rekognitionService = new AmazonRekognitionService();
    @Test
    void contextLoads() {
        System.out.println("#######################################contextLoads");
        rekognitionService.detectFaces("c:/mypic.png");
    }

}
