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

    private String oauthId;

    private String name;

    @Column(unique = true)
    private String email;

    @Builder
    public User(Long id, String oauthId, String name, String email) {
        this.id = id;
        this.oauthId = oauthId;
        this.name = name;
        this.email = email;
    }

    public User update(String name, String email) {
        this.name = name;
        this.email = email;
        return this;
    }

}
