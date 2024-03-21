package org.example.b104.domain.user.controller.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private Long id;
    private String name;
    private String email;
    private String tokenType;
    private String accessToken;
    private String refreshToken;
}
