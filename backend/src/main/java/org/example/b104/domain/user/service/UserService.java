package org.example.b104.domain.user.service;

import com.sun.jdi.request.InvalidRequestStateException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.b104.domain.oauth2.JwtTokenProvider;
import org.example.b104.domain.oauth2.OauthProvider;
import org.example.b104.domain.oauth2.UserProfile;
import org.example.b104.domain.oauth2.response.OauthTokenResponse;
import org.example.b104.domain.user.controller.response.CreateUserResponse;
import org.example.b104.domain.user.controller.response.LoginResponse;
import org.example.b104.domain.user.entity.User;
import org.example.b104.domain.user.repository.UserRepository;
import org.example.b104.domain.user.service.command.CreateUserCommand;
import org.example.b104.domain.user.service.command.LoginCommand;
import org.example.b104.global.exception.EntityNotFoundException;
import org.example.b104.global.response.ApiResponse;
import org.example.b104.global.response.BankApiResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

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
        String jwtToken = jwtTokenProvider.createAccessToken(String.valueOf(user.getId()));
        String refreshToken = jwtTokenProvider.createRefreshToken();

        return LoginResponse.builder()
                .id(user.getId())
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

        System.out.println("======api통신 시작=========" + command.getEmail());
        BankApiResponse responseEntity = WebClient.create()
                .post()
                .uri("https://finapi.p.ssafy.io/ssafy/api/v1/member/")
                .bodyValue(new MemberRequest("d16009763e1e4c82a538f3c29a090513", command.getEmail()))
                .retrieve()
                .bodyToMono(BankApiResponse.class)
                .block();
        System.out.println("=====통신완료========");

        // 응답 처리
//        System.out.println(responseEntity.getPayload().getUserKey());
        if (responseEntity != null) {
            String userKey = responseEntity.getPayload().getUserKey();
//            // userKey를 저장
            User newUser = User.createNewUser(
                    command.getEmail(),
                    bCryptPasswordEncoder.encode(command.getPassword()),
                    command.getUsername(),
                    command.getPhone(),
                    userKey
            );
//
            userRepository.save(newUser);
            return CreateUserResponse.builder()
                    .userId(newUser.getId())
                    .build();
//        }
        }


//    private MultiValueMap<String, String> userRequest (String apiKey, String userId) {
//        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
//        formData.add("apiKey", apiKey);
//        formData.add("userId", userId);
//        return formData;
//    }
        return null;
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
