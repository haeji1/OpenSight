package org.example.b104.domain.oauth2.response;


import lombok.*;

@Getter
@NoArgsConstructor
public class LoginResponse {
    private Long id;
    private String name;
    private String email;
    private String nickname;
    private String tokenType;
    private String accessToken;
    private String jwtToken;
    private String refreshToken;

    @Builder
    public LoginResponse(Long id, String name, String email, String nickname, String tokenType, String accessToken, String jwtToken, String refreshToken) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.tokenType = tokenType;
        this.accessToken = accessToken;
        this.jwtToken = jwtToken;
        this.refreshToken = refreshToken;
    }
}
