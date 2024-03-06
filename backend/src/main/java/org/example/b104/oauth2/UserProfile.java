package org.example.b104.oauth2;

import lombok.Builder;
import lombok.Getter;
import org.example.b104.domain.user.entity.User;

@Getter
public class UserProfile {
    private final String oauthId;
    private final String email;
    private final String name;

    @Builder
    public UserProfile(String oauthId, String email, String name) {
        this.oauthId = oauthId;
        this.email = email;
        this.name = name;
    }

    public User toUser() {
        return User.builder()
                .oauthId(oauthId)
                .email(email)
                .name(name)
                .build();
    }


}
