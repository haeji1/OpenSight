package org.example.b104.domain.amazon.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * Amazon S3 서비스를 사용하기 위한 클래스
 * @Author : 류진호
 */
@Service
public class AmazonS3Service {
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    private final S3Client s3Client;

    public AmazonS3Service(
            @Value("${aws.accessKeyId}") String accessKeyId,
            @Value("${aws.secretAccessKey}") String secretAccessKey){
        this.s3Client = S3Client.builder()
                //.credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKeyId, secretAccessKey)))
                .region(Region.US_EAST_1) // 아마존 서버 지역
                .build();
    }

    /**
     * S3에 파일을 업로드하는 함수
     *
     * @param filePath : 업로드할 파일의 경로
     * @return
     * @Author : 류진호
     */
    public String uploadFile(String filePath) {
        try {
            File file = new File(filePath);
            String keyName = file.getName(); // S3에 저장될 파일의 이름

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(keyName)
                    .build();

            s3Client.putObject(putObjectRequest, file.toPath());
            return "SUCCESS";
        }catch (Exception e){
            e.printStackTrace();
            return "FAILED";
        }
    }

    /**
     * S3에 저장된 JSON 파일을 다운로드하는 함수
     * 음성인식 결과를 가져올 때 Json 파일로 저장되기 때문에 사용
     * @param keyName
     * @return
     * @Author : 류진호
     */
    public String downloadJsonFile(String keyName) {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(keyName)
                    .build();

            ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(getObjectRequest);
            byte[] contentBytes = objectBytes.asByteArray();
            String jsonContent = new String(contentBytes, StandardCharsets.UTF_8);

            return jsonContent;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}