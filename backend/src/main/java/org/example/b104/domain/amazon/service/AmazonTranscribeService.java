package org.example.b104.domain.amazon.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.transcribe.TranscribeClient;
import software.amazon.awssdk.services.transcribe.model.LanguageCode;
import software.amazon.awssdk.services.transcribe.model.Media;
import software.amazon.awssdk.services.transcribe.model.MediaFormat;
import software.amazon.awssdk.services.transcribe.model.StartTranscriptionJobRequest;

import java.io.InputStream;

/**
 * Amazon Transcribe 서비스를 사용하기 위한 클래스
 * @Author : 류진호
 */
@Service
public class AmazonTranscribeService {

    private final TranscribeClient transcribeClient;
    private final S3Client s3Client;


    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    public AmazonTranscribeService(
            @Value("${aws.accessKeyId}") String accessKeyId,
            @Value("${aws.secretAccessKey}") String secretAccessKey){
        this.transcribeClient = TranscribeClient.builder()
                .region(Region.US_EAST_1) // 사용할 리전 지정
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKeyId, secretAccessKey)))
                //.credentialsProvider(DefaultCredentialsProvider.create())
                .build();

        this.s3Client = S3Client.builder()
                .region(Region.US_EAST_1) // 사용할 리전 지정
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKeyId, secretAccessKey)))
                //.credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }


    /**
     * S3에 저장된 오디오 파일을 텍스트로 변환하는 함수
     * MyTranscriptionJob.json 파일로 변환된 텍스트가 저장됨
     * 이 함수 호출 후 AWS Transcribe 서비스 내 DownloadJsonFile 함수를 호출하여 변환된 텍스트를 가져올 수 있음
     * @param objectKey : S3에 저장된 오디오 파일의 이름
     * @Author : 류진호
     */
    public void transcribeAudioFile(String objectKey) {
        // S3에서 오디오 파일을 가져옴
        InputStream inputStream = s3Client.getObject(GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build());

        // Transcribe 작업 생성
        StartTranscriptionJobRequest request = StartTranscriptionJobRequest.builder()
                .languageCode(LanguageCode.KO_KR) // 오디오 파일의 언어 코드 설정
                .mediaFormat(MediaFormat.MP3) // 오디오 파일의 형식 지정 (MP3, WAV 등)
                .media(Media.builder()
                        .mediaFileUri("s3://" + bucketName + "/" + objectKey)
                        .build())
                .transcriptionJobName("MyTranscriptionJob") // Transcribe 작업 이름 설정
                .outputBucketName(bucketName) // 텍스트 출력을 저장할 S3 버킷 이름 지정
                .build();

        // Transcribe 작업 시작
        transcribeClient.startTranscriptionJob(request);
    }

}
