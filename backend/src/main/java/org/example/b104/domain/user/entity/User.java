package org.example.b104.domain.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String password;

    private String oauthId;

    private String username;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phoneNumber;

    private String userKey;

    private String institutionCode;
    private Date created;
    private Date modified;
    private String emailPrefix;
    private String uniqueFaceId;

    @Builder
    public User(Long id, String oauthId, String name, String email) {
        this.userId = id;
        this.oauthId = oauthId;
        this.username = name;
        this.email = email;
    }

    @Builder
    public User(Long id, String oauthId, String name, String email, String userKey) {
        this.userId = id;
        this.oauthId = oauthId;
        this.username = name;
        this.email = email;
        this.userKey = userKey;
    }

    public User update(String name, String email) {
        this.username = name;
        this.email = email;
        return this;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public User updateUserKey(String userKey) {
        this.userKey = userKey;
        return this;
    }

    public static User createNewUser(
            String email,
            String password,
            String username,
            String phone,
            String userKey,
            Date created,
            Date modified,
            String institutionCode,
            String emailPrefix

    ) {
        User user = new User();
        user.email = email;
        user.password = password;
        user.username = username;
        user.phoneNumber = phone;
        user.oauthId = "일반 회원가입";
        user.userKey = userKey;
        user.created = created;
        user.modified = modified;
        user.institutionCode = institutionCode;
        user.emailPrefix = emailPrefix;
        return user;
    }

    public static User createNewUser(
            String email,
            String password,
            String username,
            String phone,
            String userKey,
            Date created,
            Date modified,
            String institutionCode,
            String emailPrefix,
            String uniqueFaceId

    ) {
        User user = new User();
        user.email = email;
        user.password = password;
        user.username = username;
        user.phoneNumber = phone;
        user.oauthId = "일반 회원가입";
        user.userKey = userKey;
        user.created = created;
        user.modified = modified;
        user.institutionCode = institutionCode;
        user.emailPrefix = emailPrefix;
        user.uniqueFaceId = uniqueFaceId;
        return user;
    }

    public static User createNewUser(
            String email,
            String password,
            String username,
            String userKey,
            Date created,
            Date modified,
            String institutionCode,
            String emailPrefix
    ) {
        User user = new User();
        user.email = email;
        user.password = password;
        user.username = username;
        user.oauthId = "소셜 회원가입";
        user.userKey = userKey;
        user.created = created;
        user.modified = modified;
        user.institutionCode = institutionCode;
        user.emailPrefix = emailPrefix;
        return user;
    }

    public void updateUser(String password, String username) {
        this.password = password;
        this.username = username;
    }
    public void updateFaceId(String uniqueFaceId) {
        this.uniqueFaceId = uniqueFaceId;
    }

}
