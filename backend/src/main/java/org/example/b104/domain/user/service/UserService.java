package org.example.b104.domain.user.service;

import com.sun.jdi.request.InvalidRequestStateException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.b104.domain.account.controller.response.RegisterAccountMemberResponse;
import org.example.b104.domain.account.service.AccountService;
import org.example.b104.domain.account.service.command.RegisterAccountMemberCommand;
import org.example.b104.domain.oauth2.JwtTokenProvider;
import org.example.b104.domain.user.controller.response.*;
import org.example.b104.domain.user.entity.User;
import org.example.b104.domain.user.repository.UserRepository;
import org.example.b104.domain.user.service.command.*;
import org.example.b104.global.exception.EntityNotFoundException;
import org.example.b104.global.response.BankApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    private final AccountService accountService;


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
                        .build()
        );
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

    @Data
    static class MemberRequest {
        private String apiKey;
        private String userId;

        public MemberRequest(String apiKey, String userId) {
            this.apiKey = apiKey;
            this.userId = userId;
        }
    }

}
