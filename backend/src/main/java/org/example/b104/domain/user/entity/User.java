package org.example.b104.domain.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor
//@AllArgsConstructor
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

//    private String institutionCode;
//
//    private String userKey;
//    private String created;
//    private String modified;
//    private String emailPrefix;
//    private String uniqueFaceId;
//    private String phoneNumber;

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
            String username
    ) {
        User user = new User();
        user.email = email;
        user.password = password;
        user.username = username;
        return user;
    }

}
