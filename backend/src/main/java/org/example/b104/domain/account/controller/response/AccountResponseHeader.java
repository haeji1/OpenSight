package org.example.b104.domain.account.controller.response;

import lombok.*;


@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountResponseHeader {
    private String responseCode;
    private String responseMessage;
    private String apiName;
    private String transmissionDate;
    private String transmissionTime;
    private String institutionCode;
    private String apiKey;
    private String apiServiceCode;
    private String institutionTransactionUniqueNo;
}
