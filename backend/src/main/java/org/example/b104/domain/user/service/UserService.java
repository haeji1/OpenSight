package org.example.b104.domain.user.service;

import com.sun.jdi.request.InvalidRequestStateException;
import lombok.RequiredArgsConstructor;
import org.example.b104.domain.account.controller.response.RegisterAccountMemberResponse;
import org.example.b104.domain.account.service.AccountService;
import org.example.b104.domain.user.service.command.FaceAuthCommand;
import org.example.b104.domain.account.service.command.RegisterAccountMemberCommand;
import org.example.b104.domain.amazon.service.AmazonRekognitionService;
import org.example.b104.domain.amazon.service.AmazonS3Service;
import org.example.b104.domain.oauth2.JwtTokenProvider;
import org.example.b104.domain.user.controller.response.*;
import org.example.b104.domain.user.entity.User;
import org.example.b104.domain.user.repository.UserRepository;
import org.example.b104.domain.user.service.command.*;
import org.example.b104.global.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.rekognition.model.FaceMatch;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    private final AccountService accountService;

    @Autowired
    private final AmazonRekognitionService amazonRekognitionService;
    @Autowired
    private final AmazonS3Service s3Service;
    //private static final String UPLOAD_DIR = "C:\\OPENSIGHT\\profileImages";
    private static final String UPLOAD_DIR = "profileImages";

    public String getPrefixOfEmail(String email) {
        String[] parts = email.split("@");
        if (parts.length > 0) {
            return parts[0];
        }
        return null;
    }

    @Transactional(readOnly = false)
    public LoginResponse Login(LoginCommand command) {

        BCryptPasswordEncoder bCryptPasswordEncoder  = new BCryptPasswordEncoder();

        // 입력 유효성 검사
        if (command.getUserEmail() == null || command.getUserPassword() == null) {
            throw new InvalidRequestStateException("이메일과 비밀번호는 필수 입력값입니다.");
        }

        User user = userRepository.findByEmail(command.getUserEmail());
        if (user == null || !bCryptPasswordEncoder.matches(command.getUserPassword(), user.getPassword())) {
            throw new EntityNotFoundException("로그인 실패");
        }

        // JWT 토큰 생성
        String jwtToken = jwtTokenProvider.createAccessToken(String.valueOf(user.getUserId()));
        String refreshToken = jwtTokenProvider.createRefreshToken();

        return LoginResponse.builder()
                .id(user.getUserId())
                .name(user.getUsername())
                .email(user.getEmail())
                .tokenType("Bearer")
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();

    }


    @Transactional(readOnly = false)
    public LoginResponse faceLogin(FaceLoginCommand command) {
        try {
            File uploadDir = new File("src/main/resources/" + UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // 파일을 지정된 디렉토리에 저장
            /*String fileName = command.getRequestImage().getOriginalFilename();

            File destFile = new File(UPLOAD_DIR + File.separator + fileName);
            command.getRequestImage().transferTo(destFile);

            File file = new File(UPLOAD_DIR + File.separator + fileName);
            String keyName = file.getName();

            s3Service.uploadFile(UPLOAD_DIR + File.separator + fileName);
            */
            String fileName = command.getRequestImage().getOriginalFilename();
            String keyName = fileName;

            s3Service.uploadFile(command.getRequestImage(),"cloud-open-sight-ue1");
            List<FaceMatch> matchList = amazonRekognitionService.recognizeFace("cloud-open-sight-collection", "cloud-open-sight-ue1", keyName);

            if (matchList.isEmpty())
                throw new EntityNotFoundException("매치리스트가 없습니다");

            User user = userRepository.findByUniqueFaceId(matchList.get(0).face().faceId());
            if (user == null) {
                throw new EntityNotFoundException("로그인 실패");
            }

            // JWT 토큰 생성
            String jwtToken = jwtTokenProvider.createAccessToken(String.valueOf(user.getUserId()));
            String refreshToken = jwtTokenProvider.createRefreshToken();

            return LoginResponse.builder()
                    .id(user.getUserId())
                    .name(user.getUsername())
                    .email(user.getEmail())
                    .tokenType("Bearer")
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            throw new EntityNotFoundException("로그인 실패");
        }
    }


    @Transactional(readOnly = false)
    public CreateUserResponse createUser(CreateUserCommand command) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        /*System.out.println("======api통신 시작=========" + command.getEmail());
        String emailPrefix = command.getEmail().substring(0, Math.min(command.getEmail().length(), 10));
        BankApiResponse responseEntity = WebClient.create()
                .post()
                .uri("https://finapi.p.ssafy.io/ssafy/api/v1/member/")
                .bodyValue(new MemberRequest("d16009763e1e4c82a538f3c29a090513", command.getEmail()))
                .retrieve()
                .bodyToMono(BankApiResponse.class)
                .block();
        System.out.println("=====통신완료========");
*/
        //String emailPrefix = command.getEmail().substring(0, Math.min(command.getEmail().length(), 10));

//        if (responseEntity != null) {
//            String userKey = responseEntity.getPayload().getUserKey();
//            Date created = responseEntity.getPayload().getCreated();
//            Date modified = responseEntity.getPayload().getModified();
//            String institutionCode = responseEntity.getPayload().getInstitutionCode();
//
//            User newUser = User.createNewUser(
//                    command.getEmail(),
//                    bCryptPasswordEncoder.encode(command.getPassword()),
//                    command.getUsername(),
//                    command.getPhone(),
//                    userKey,
//                    created,
//                    modified,
//                    institutionCode,
//                    emailPrefix
//            );
//
//            userRepository.save(newUser);
//            return CreateUserResponse.builder()
//                    .userId(newUser.getUserId())
//                    .build();
//        }

        RegisterAccountMemberResponse response = accountService.registerAccountMember(
                RegisterAccountMemberCommand.builder()
                        .userId(command.getEmail())
                        .build());
        try {
            if (response != null) {
                String userKey = response.getUserKey();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");


                Date created = formatter.parse(response.getCreated());
                Date modified = formatter.parse(response.getModified());
                String institutionCode = response.getInstitutionCode();

                User newUser = User.createNewUser(
                        command.getEmail(),
                        bCryptPasswordEncoder.encode(command.getPassword()),
                        command.getUsername(),
                        command.getPhone(),
                        userKey,
                        created,
                        modified,
                        institutionCode,
                        getPrefixOfEmail(command.getEmail())
                );

                userRepository.save(newUser);
                return CreateUserResponse.builder()
                        .userId(newUser.getUserId())
                        .build();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Transactional(readOnly = false)
    public CreateUserResponse createUserWithProfileImage(CreateUserCommand command, MultipartFile profileImage) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        RegisterAccountMemberResponse response = accountService.registerAccountMember(
                RegisterAccountMemberCommand.builder()
                        .userId(command.getEmail())
                        .build()
        );

        String faceId = null;

        try {
            // 디렉토리가 없으면 생성
//            File uploadDir = new File(UPLOAD_DIR);
//            if (!uploadDir.exists()) {
//                uploadDir.mkdirs();
//            }
//
//            // 파일을 지정된 디렉토리에 저장
//            String fileName = command.getEmail() + profileImage.getOriginalFilename();
//            File destFile = new File(UPLOAD_DIR + File.separator + fileName);
//            profileImage.transferTo(destFile);
//
//
//            String fn = s3Service.uploadFile(UPLOAD_DIR + File.separator + fileName);


            String fileName = profileImage.getOriginalFilename();
            String keyName = fileName;
            s3Service.uploadFile(profileImage,"cloud-open-sight-ue1");

            faceId = amazonRekognitionService.registeruser("cloud-open-sight-ue1", fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


        try {
            if (response != null) {
                String userKey = response.getUserKey();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date created = formatter.parse(response.getCreated());
                Date modified = formatter.parse(response.getModified());
                String institutionCode = response.getInstitutionCode();

                User newUser = User.createNewUser(
                        command.getEmail(),
                        bCryptPasswordEncoder.encode(command.getPassword()),
                        command.getUsername(),
                        command.getPhone(),
                        userKey,
                        created,
                        modified,
                        institutionCode,
                        getPrefixOfEmail(command.getEmail()),
                        faceId
                );

                userRepository.save(newUser);
                return CreateUserResponse.builder()
                        .userId(newUser.getUserId())
                        .build();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    @Transactional(readOnly = false)
    public UpdateUserResponse updateUser(UpdateUserCommand command) {
        User user = userRepository.findByEmail(command.getEmail());
        if (user != null) {
            user.updateUser(command.getPassword(), command.getUsername());
            return UpdateUserResponse.builder()
                    .userId(user.getUserId())
                    .build();
        }
        throw new EntityNotFoundException("존재하지 않는 유저입니다.");
    }

    @Transactional(readOnly = false)
    public UpdateFaceResponse updateFace(UpdateFaceCommand command, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));

        String faceId = null;

        try {
            // 디렉토리가 없으면 생성
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // 파일을 지정된 디렉토리에 저장
            String fileName = user.getEmail() + command.getRequestImage().getOriginalFilename();
            File destFile = new File(UPLOAD_DIR + File.separator + fileName);
            command.getRequestImage().transferTo(destFile);


            String fn = s3Service.uploadFile(UPLOAD_DIR + File.separator + fileName);
            faceId = amazonRekognitionService.registeruser("cloud-open-sight-ue1", fileName);
        } catch (IOException e) {
            e.printStackTrace();

            throw new EntityNotFoundException("얼굴 등록 실패");
        }
        user.updateFaceId(faceId);
        return UpdateFaceResponse.builder()
                    .result("얼굴 등록 성공")
                    .build();
    }

    @Transactional(readOnly = false)
    public RegisterSimplePasswordResponse registerSimplePassword(String token, RegisterSimplePasswordCommand command) {
        User user = getUserFromToken(token);
        user.updateSimplePassword(command.getSimplePassword());
        return RegisterSimplePasswordResponse.builder()
                .isSuccess(true)
                .build();
    }

    @Transactional(readOnly = false)
    public ConfirmSimplePasswordResponse confirmSimplePassword(String token, ConfirmSimplePasswordCommand command) {
        User user = getUserFromToken(token);
        String simplePassword = user.getSimplePassword();
        System.out.println("==========simplePassword"+simplePassword+" "+command.getSimplePassword());
        if (simplePassword.equals(command.getSimplePassword())) {
            System.out.println("일치");
            return ConfirmSimplePasswordResponse.builder()
                    .isMatched(true)
                    .build();
        } else {
            System.out.println("불일치");
            System.out.println("===============================");
            System.out.println("유저의 simplePassword"+simplePassword);
            System.out.println("들어온 simplePassword"+command.getSimplePassword());
            return ConfirmSimplePasswordResponse.builder()
                    .isMatched(false)
                    .build();
        }
    }

    @Transactional(readOnly = false)
    public FindPasswordResponse findPassword(FindPasswordCommand command) {
        User user = userRepository.findByEmailAndPhoneNumber(command.getEmail(), command.getPhone())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));
        return FindPasswordResponse.builder()
                .isSuccess(true)
                .build();
    }

    @Transactional(readOnly = false)
    public DeleteUserResponse deleteUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        BCryptPasswordEncoder bCryptPasswordEncoder  = new BCryptPasswordEncoder();
        if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
            userRepository.delete(user);
        }
        return DeleteUserResponse.builder()
                .userId(user.getUserId())
                .build();
    }

    @Transactional(readOnly = false)
    public UpdatePasswordResponse updatePassword(UpdatePasswordCommand command, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));
        user.updatePassword(command.getPassword());
        return UpdatePasswordResponse.builder()
                .isSuccess(true)
                .build();
    }

    @Transactional(readOnly = true)
    public FindEmailResponse findEmail(FindEmailCommand command) {
        User user=  userRepository.findByPhoneNumber(command.getPhone())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));
        return FindEmailResponse.builder()
                .email(user.getEmail())
                .build();
    }

    @Transactional(readOnly = false)
    public RegisterAccountResponse registerAccount(RegisterAccountCommand command, String token) {
        User user = getUserFromToken(token);
        user.updateAccount(command.getAccountNo(), command.getBankCode());
        return RegisterAccountResponse.builder()
                .isSuccess(true)
                .build();
    }

//    @Data
//    static class MemberRequest {
//        private String apiKey;
//        private String userId;
//
//        public MemberRequest(String apiKey, String userId) {
//            this.apiKey = apiKey;
//            this.userId = userId;
//        }
//    }

    private User getUserFromToken(String token) {
        String userId = jwtTokenProvider.getPayload(token);
        Long userIdLong = Long.parseLong(userId);
        User user = userRepository.findById(userIdLong)
                .orElseThrow(()-> new EntityNotFoundException("존재하지 않는 유저입니다."));
        return user;
    }

    public FaceAuthResponse faceAuth(FaceAuthCommand command, Long userId) {
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        // 파일을 지정된 디렉토리에 저장
        String fileName = command.getRequestImage().getOriginalFilename();
        try {
            File destFile = new File(UPLOAD_DIR + File.separator + fileName);
            command.getRequestImage().transferTo(destFile);

            File file = new File(UPLOAD_DIR + File.separator + fileName);
            String keyName = file.getName();

            s3Service.uploadFile(UPLOAD_DIR + File.separator + fileName);
            List<FaceMatch> matchList = amazonRekognitionService.recognizeFace("cloud-open-sight-collection", "cloud-open-sight-ue1", keyName);

            if (matchList.isEmpty())
                throw new EntityNotFoundException("매치리스트가 없습니다");

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));
            if (user.getUniqueFaceId().equals(matchList.get(0).face().faceId())) {
                return FaceAuthResponse.builder()
                        .result("success")
                        .userId(user.getUserId())
                        .build();
            }
            else
                throw new EntityNotFoundException("얼굴인증 실패");

        }catch (Exception e) {
            e.printStackTrace();
            throw new EntityNotFoundException("로그인 실패");
        }
    }
}
