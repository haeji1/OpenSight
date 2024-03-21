package org.example.b104.domain.account.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.b104.domain.user.entity.User;

@Entity
@Table(name = "accounts")
@NoArgsConstructor
@Getter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String simplePassword;
    private String accountTypeUniqueNo;
    private String bankCode;
    private String bankName;
    private String accountTypeCode;
    private String accountTypeName;
    private String accountName;
    private String accountNo;
    //private String userName;
    private String accountCreatedDate;
    private String accountExpiryDate;
    private int dailyTransferLimit;
    private int oneTimeTransferLimit;
    private int accountBalance;
    private String lastTransactionDate;
}
