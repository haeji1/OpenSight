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
                .region(Region.US_EAST_1) // Your desired AWS region
                .build();
    }

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