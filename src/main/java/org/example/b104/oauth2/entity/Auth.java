package org.example.b104.oauth2.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.b104.domain.user.entity.User;

@Getter
@Builder
@RequiredArgsConstructor
@Table(name = "auth")
@Entity
public class Auth {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String refreshToken;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Auth(String refreshToken, User user) {
        this.refreshToken = refreshToken;
        this.user = user;
    }

    public void updateRefresh(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
