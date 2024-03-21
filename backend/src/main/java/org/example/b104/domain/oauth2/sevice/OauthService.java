package org.example.b104.domain.oauth2.sevice;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.b104.domain.oauth2.*;
import org.example.b104.domain.oauth2.entity.Auth;
import org.example.b104.domain.oauth2.repository.AuthRepository;
import org.example.b104.domain.oauth2.response.SocialLoginResponse;
import org.example.b104.domain.oauth2.response.OauthTokenResponse;
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
import java.util.Collections;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OauthService {

    private final JwtTokenProvider jwtTokenProvider;

    private final InMemoryProviderRepository inMemoryProviderRepository;

    private final UserRepository userRepository;

    private final AuthRepository authRepository;
    public SocialLoginResponse socialLogin(String providerName, String code) {

        BCryptPasswordEncoder bCryptPasswordEncoder  = new BCryptPasswordEncoder();
        System.out.println("========="+providerName);

        // 프론트에서 providerName 받아 InMemoryProviderRepository에서 OauthProvider 가져오기
        OauthProvider provider = inMemoryProviderRepository.findByProviderName(providerName);

        // 1. jwtToken 가져오기
        OauthTokenResponse tokenResponse = getToken(code, provider);
//        System.out.println("****tokenResponse****"+tokenResponse.getAccessToken());
        OauthTokenResponse.builder()
                .accessToken(tokenResponse.getAccessToken())
                .tokenType(tokenResponse.getTokenType())
                .scope(tokenResponse.getScope());

        // 2. 유저 정보 가져오기
        UserProfile userProfile = getUserProfile(providerName, tokenResponse, provider);

        if (userRepository.findByEmail(userProfile.getEmail()) == null) {
            System.out.println("======api통신 시작=========" + userProfile.getEmail());
            String emailPrefix = userProfile.getEmail().substring(0, Math.min(userProfile.getEmail().length(), 10));
            BankApiResponse responseEntity = WebClient.create()
                    .post()
                    .uri("https://finapi.p.ssafy.io/ssafy/api/v1/member/")
                    .bodyValue(new OauthService.MemberRequest("96f07d05ddb44471a9f51ab483286563", userProfile.getEmail()))
                    .retrieve()
                    .bodyToMono(BankApiResponse.class)
                    .block();
            System.out.println("=====통신완료========");

            if (responseEntity != null) {
                String userKey = responseEntity.getPayload().getUserKey();
                Date created = responseEntity.getPayload().getCreated();
                Date modified = responseEntity.getPayload().getModified();
                String institutionCode = responseEntity.getPayload().getInstitutionCode();

                User newUser = User.createNewUser(
                        userProfile.getEmail(),
                        bCryptPasswordEncoder.encode("socialLogin"),
                        userProfile.getName(),
                        userKey,
                        created,
                        modified,
                        institutionCode,
                        emailPrefix
                );
                // 3. 유저 DB에 저장
//            User user = saveOrUpdate(userProfile);
                User user = userRepository.save(newUser);

                // JWT 토큰 만들기
                String jwtToken = jwtTokenProvider.createAccessToken(String.valueOf(user.getUserId()));
                String refreshToken = jwtTokenProvider.createRefreshToken();

                Auth auth = Auth.builder()
                        .user(user)
                        .refreshToken(refreshToken)
                        .build();

                saveOrUpdate(auth, user);

//        System.out.println("jwt토큰"+jwtToken);
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

        }

        else {
            User user = saveOrUpdate(userProfile);
            // JWT 토큰 만들기
            String jwtToken = jwtTokenProvider.createAccessToken(String.valueOf(user.getUserId()));
            String refreshToken = jwtTokenProvider.createRefreshToken();

            Auth auth = Auth.builder()
                    .user(user)
                    .refreshToken(refreshToken)
                    .build();

            saveOrUpdate(auth, user);

//        System.out.println("jwt토큰"+jwtToken);
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

//        System.out.println("===유저정보 출력==="+user.getName()+user.getEmail());


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
        User user = userRepository.findByOauthId(userProfile.getOauthId())
                .map(entity -> entity.update(
                        userProfile.getName(), userProfile.getEmail()))
                .orElseGet(userProfile::toUser);
    return userRepository.save(user);
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
