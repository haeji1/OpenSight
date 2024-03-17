package org.example.b104.domain.account.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.b104.domain.user.entity.User;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
public class Account {
    @Id
    private Long accountId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getAccountId() {
        return accountId;
    }
}
