package org.example.b104.domain.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;

    private String oauthId;

    private String username;

    @Column(unique = true)
    private String email;

    private String phone;

//    private String institutionCode;
//
//    private String userKey;
//    private String created;
//    private String modified;
//    private String emailPrefix;
//    private String uniqueFaceId;

    @Builder
    public User(Long id, String oauthId, String name, String email) {
        this.id = id;
        this.oauthId = oauthId;
        this.username = name;
        this.email = email;
    }

    public User update(String name, String email) {
        this.username = name;
        this.email = email;
        return this;
    }

    public static User createNewUser(
            String email,
            String password,
            String username,
            String phone
    ) {
        User user = new User();
        user.email = email;
        user.password = password;
        user.username = username;
        user.phone = phone;
        user.oauthId = "일반 회원가입";
        return user;
    }

}
