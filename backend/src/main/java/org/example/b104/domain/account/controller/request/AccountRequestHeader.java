package org.example.b104.domain.account.controller.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Builder
@Getter
@AllArgsConstructor
public class AccountHeader{
    private String apiName;
    private String transmissionDate;
    private String transmissionTime;
    private String institutionCode;
    private String fintechAppNo;
    private String apiServiceCode;
    private String institutionTransactionUniqueNo;
    private String apiKey;
    private String userKey;

    public void generateInstitutionTransactionUniqueNo(){ // (YYYYMMDD + HHMMSS + 일련번호 6자리)
        this.institutionTransactionUniqueNo = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + String.format("%06d", new Random().nextInt(1000000));
    }
}
