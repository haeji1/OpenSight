package org.example.b104.oauth2;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Getter
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "oauth2")
public class OauthProperties {
    private final Map<String, User> user = new HashMap<>();
    private final Map<String, Provider> provider = new HashMap<>();

    @Data
    public static class User {
        private String clientId;
        private String clientSecret;
        private String redirectUri;
    }

    @Data
    public static class Provider {
        private String tokenUri;
        private String userInfoUri;
        private String userNameAttribute;
    }

}
