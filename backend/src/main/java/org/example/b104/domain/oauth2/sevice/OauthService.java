package org.example.b104.domain.oauth2.sevice;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.b104.domain.account.controller.response.RegisterAccountMemberResponse;
import org.example.b104.domain.account.service.AccountService;
import org.example.b104.domain.account.service.command.RegisterAccountMemberCommand;
import org.example.b104.domain.oauth2.*;
import org.example.b104.domain.oauth2.entity.Auth;
import org.example.b104.domain.oauth2.repository.AuthRepository;
import org.example.b104.domain.oauth2.response.SocialLoginResponse;
import org.example.b104.domain.oauth2.response.OauthTokenResponse;
import org.example.b104.domain.user.controller.response.CreateUserResponse;
import org.example.b104.domain.user.entity.User;
import org.example.b104.domain.user.repository.UserRepository;
import org.example.b104.domain.user.service.UserService;
import org.example.b104.global.response.BankApiResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OauthService {

    private final JwtTokenProvider jwtTokenProvider;

    private final InMemoryProviderRepository inMemoryProviderRepository;

    private final AccountService accountService;

    private final UserRepository userRepository;

    private final AuthRepository authRepository;
    public SocialLoginResponse socialLogin(String providerName, String code) {

        BCryptPasswordEncoder bCryptPasswordEncoder  = new BCryptPasswordEncoder();
        System.out.println("========="+providerName);

        // 프론트에서 providerName 받아 InMemoryProviderRepository에서 OauthProvider 가져오기
        OauthProvider provider = inMemoryProviderRepository.findByProviderName(providerName);

        // 1. accessToken 가져오기
        OauthTokenResponse tokenResponse = getToken(code, provider);
//        System.out.println("****tokenResponse****"+tokenResponse.getAccessToken());
        OauthTokenResponse.builder()
                .accessToken(tokenResponse.getAccessToken())
                .tokenType(tokenResponse.getTokenType())
                .scope(tokenResponse.getScope());

        // 2. 유저 정보 가져오기
        UserProfile userProfile = getUserProfile(providerName, tokenResponse, provider);
        System.out.println("**********User정보***********"+userProfile.getEmail());
        System.out.println("**********user정보**********"+userProfile.getName());
        System.out.println("====userProfile.getEmail===="+userProfile.getEmail());


        if (userRepository.findByEmail(userProfile.getEmail()) == null) {
            System.out.println("==================email이 null임==================");

            RegisterAccountMemberResponse response = accountService.registerAccountMember(
                    RegisterAccountMemberCommand.builder()
                            .userId(userProfile.getEmail())
                            .build()
            );
            System.out.println("============RegisterAccountMemberResponse====="+response.getUserId());
            System.out.println("============RegisterAccountMemberResponse====="+response.getUserKey());
            System.out.println("============RegisterAccountMemberResponse====="+response.getUserName());

            try {
                if (response != null) {
                    String userKey = response.getUserKey();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");


                    Date created = formatter.parse(response.getCreated());
                    Date modified = formatter.parse(response.getModified());
                    String institutionCode = response.getInstitutionCode();

                    User newUser = User.createNewUser(
                            userProfile.getEmail(),
                            bCryptPasswordEncoder.encode("socialLogin"),
                            userProfile.getName(),
                            userKey,
                            created,
                            modified,
                            institutionCode,
                            getPrefixOfEmail(userProfile.getEmail())
                    );

                    User user = userRepository.save(newUser);
                    CreateUserResponse.builder()
                            .userId(newUser.getUserId())
                            .build();

                    // JWT 토큰 만들기
                    String jwtToken = jwtTokenProvider.createAccessToken(String.valueOf(user.getUserId()));
                    String refreshToken = jwtTokenProvider.createRefreshToken();

                    Auth auth = Auth.builder()
                            .user(user)
                            .refreshToken(refreshToken)
                            .build();

                    saveOrUpdate(auth, user);

                    return SocialLoginResponse.builder()
                            .id(user.getUserId())
                            .name(user.getUsername())
                            .email(user.getEmail())
                            .tokenType("Bearer")
                            .accessToken(tokenResponse.getAccessToken())
                            .jwtToken(jwtToken)
                            .refreshToken(refreshToken)
                            .build();
                }
            }catch (Exception e) {
                System.out.println("===========회원가입중 에러===========");
                e.printStackTrace();
            }


        } else {
            System.out.println("======email이 null이 아님======");
            User user = saveOrUpdate(userProfile);
            System.out.println("**********saveOrUpdate로직 끝**************");
            // JWT 토큰 만들기
            String jwtToken = jwtTokenProvider.createAccessToken(String.valueOf(user.getUserId()));
            String refreshToken = jwtTokenProvider.createRefreshToken();

            Auth auth = Auth.builder()
                    .user(user)
                    .refreshToken(refreshToken)
                    .build();

            saveOrUpdate(auth, user);


            return SocialLoginResponse.builder()
                    .id(user.getUserId())
                    .name(user.getUsername())
                    .email(user.getEmail())
                    .tokenType("Bearer")
                    .accessToken(tokenResponse.getAccessToken())
                    .jwtToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        }

        return null;
    }

    // 가져온 유저 정보 DB에 저장
    private UserProfile getUserProfile(String providerName, OauthTokenResponse tokenResponse, OauthProvider provider) {
        System.out.println("*****start******");
        Map<String, Object> userAttributes = getUserAttributes(provider, tokenResponse);
//        System.out.println("*******userAttrivutes:*********"+userAttributes);
        // 유저 정보(map)를 통해 UserProfile 만들기
        return OauthAttributes.extract(providerName, userAttributes);
    }

    // OAuth 서버에서 유저 정보 map으로 가져오기
    private Map<String, Object> getUserAttributes(OauthProvider provider, OauthTokenResponse tokenResponse) {
        System.out.println("====== webClient 통신====="+provider+tokenResponse);
        System.out.println("========유저정보 가져오기==========");
        return  WebClient.create()
                .get()
                .uri(provider.getUserInfoUrl())
                .headers(header -> header.setBearerAuth(tokenResponse.getAccessToken()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }

    private OauthTokenResponse getToken(String code, OauthProvider provider) {
        return WebClient.create()
                .post()
                .uri(provider.getTokenUrl())
                .headers(header -> {
                    header.setBasicAuth(provider.getClientId(), provider.getClientSecret());
                    header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                })
                .bodyValue(tokenRequest(code, provider))
                .retrieve()
                .bodyToMono(OauthTokenResponse.class)
                .block();
    }

    private MultiValueMap<String, String> tokenRequest(String code, OauthProvider provider) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("code", code);
        formData.add("grant_type", "authorization_code");
        formData.add("redirect_uri", provider.getRedirectUrl());
        return formData;
    }


private User saveOrUpdate(UserProfile userProfile) {
    // 이메일을 기준으로 사용자를 조회
    User existingUser = userRepository.findByEmail(userProfile.getEmail());

    if (existingUser != null) {
        // 이미 존재하는 이메일인 경우, 해당 사용자의 정보를 업데이트
        User user = existingUser;
        user.update(userProfile.getName(), userProfile.getEmail());
        return userRepository.save(user);
    } else {
        // 존재하지 않는 경우, 새로운 사용자 생성
        User newUser = userProfile.toUser();
        return userRepository.save(newUser);
    }
}

    private User saveOrUpdate(User newUser) {
        User user = userRepository.findByEmail(newUser.getEmail());
        return userRepository.save(user);

    }

    private Auth saveOrUpdate(Auth authorization, User user) {

        Auth auth = authRepository.findByUserUserId(user.getUserId())
                .orElse(Auth.builder().user(user).build()); // 사용자를 찾지 못하면 새로운 Auth 엔티티 생성
        auth.updateRefresh(authorization.getRefreshToken()); // 새로운 refresh token으로 갱신
        return authRepository.save(auth);
    }

public String getPrefixOfEmail(String email) {
    String[] parts = email.split("@");
    if (parts.length > 0) {
        return parts[0];
    }
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
