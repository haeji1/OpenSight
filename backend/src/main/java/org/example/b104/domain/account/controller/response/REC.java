package org.example.b104.domain.account.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
public class REC {
    public String accountTypeUniqueNo;
    public String bankCode;
    public String bankName;
    public String accountTypeCode;
    public String accountTypeName;
    public String accountName;

    public String accountNo;

    public String accountCreatedDate;
    public String accountExpiryDate;

    public String userName;

    public Long dailyTransferLimit;// 1일 이체한도
    public Long oneTimeTransferLimit;// 1회 이체한도
    public Long accountBalance;// 최종 잔액


}
