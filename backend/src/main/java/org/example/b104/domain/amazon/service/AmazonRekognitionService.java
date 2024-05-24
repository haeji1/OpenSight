package org.example.b104.domain.amazon.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;


/**
 * Amazon Rekognition 서비스를 사용하기 위한 클래스
 *
 * @Author : 류진호
 */
@Service
public class AmazonRekognitionService {

    private final RekognitionClient rekognitionClient;

    public AmazonRekognitionService(
            @Value("${aws.accessKeyId}") String accessKeyId,
            @Value("${aws.secretAccessKey}") String secretAccessKey){

        this.rekognitionClient = RekognitionClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKeyId, secretAccessKey)))
                .region(Region.US_EAST_1)
                .build();
    }

    /**
     * 컬렉션을 추가하는 함수
     * 컬렉션 : 얼굴을 저장하는 공간
     *
     * @param collectionName
     * @Author : 류진호
     */
    public String createCollection(String collectionName) {
        CreateCollectionRequest request = CreateCollectionRequest.builder()
                .collectionId(collectionName)
                .build();

        CreateCollectionResponse response = rekognitionClient.createCollection(request);
        return response.collectionArn();
    }

    /**
     * 얼굴을 검출하는 함수
     * 이미지 속에 얼굴이 있는지, 얼굴이 몇개인지 등을 검출함. S3에서 이미지를 가져오는 것이 아님!!
     * 디렉토리 경로에서 이미지를 가져옴
     *
     * @param imagePath
     * @Author : 류진호
     */
    public void detectFaces(String imagePath) {
        try {
            File imageFile = new File(imagePath);
            InputStream imageStream = new FileInputStream(imageFile);
            Image image = Image.builder()
                    .bytes(SdkBytes.fromInputStream(imageStream))
                    .build();

            DetectFacesRequest request = DetectFacesRequest.builder()
                    .image(image)
                    .build();

            DetectFacesResponse response = rekognitionClient.detectFaces(request);

            List<FaceDetail> faceDetails = response.faceDetails();
            for (FaceDetail faceDetail : faceDetails) {
                System.out.println("Detected face: " + faceDetail);
            }
        } catch (RekognitionException | FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }


    /**
     * 컬렉션에 S3에 저장된 이미지를 통해 얼굴을 등록하는 함수
     *
     * @param imageS3Bucket S3 버킷 이름 현재 서버에서 쓰는 이름은 "cloud-open-sight-ue1"
     * @param imageS3Key S3 버킷에 저장된 이미지 이름 (ex: mypic.png)
     * @return
     * @Author : 류진호
     */
    public String registeruser(String imageS3Bucket, String imageS3Key) {
        Image image = Image.builder()
                .s3Object(S3Object.builder()
                        .bucket(imageS3Bucket)
                        .name(imageS3Key)
                        .build())
                .build();
        IndexFacesRequest request = IndexFacesRequest.builder()
                .collectionId("cloud-open-sight-collection")//collectionId)
                .image(image)
                .build();

        IndexFacesResponse response = rekognitionClient.indexFaces(request);
        return response.faceRecords().get(0).face().faceId();
    }


    //
    //arn:aws:s3:::cloud-open-sight/mypic.png

    /**
     * S3에 업로드 된 이미지를 통해 컬렉션에 가입된 유저와 비교하는 함수
     * S3에 이미지를 업로드 한 후 사용해야함
     *
     * @param collectionId 사용되는 컬렉션 ID (ex: cloud-open-sight-collection)
     * @param imageS3Bucket S3 버킷 이름 (ex: cloud-open-sight-ue1)
     * @param imageS3Key 비교되는 이미지
     * @return
     * @Author : 류진호
     */
    public List<FaceMatch> recognizeFace(String collectionId, String imageS3Bucket, String imageS3Key) {
        Image image = Image.builder()
                .s3Object(S3Object.builder()
                        .bucket(imageS3Bucket)
                        .name(imageS3Key)
                        .build())
                .build();

        SearchFacesByImageRequest request = SearchFacesByImageRequest.builder()
                .collectionId(collectionId)
                .image(image)
                .build();

        SearchFacesByImageResponse response = rekognitionClient.searchFacesByImage(request);
        return response.faceMatches();
    }
}
