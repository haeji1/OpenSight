package org.example.b104.oauth2.response;


import lombok.*;

@Getter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private Long id;
    private String name;
    private String email;
    private String nickname;
    private String tokenType;
    private String accessToken;
    private String refreshToken;

}
