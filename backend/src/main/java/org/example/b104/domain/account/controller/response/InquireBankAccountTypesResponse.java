package org.example.b104.domain.account.controller.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@AllArgsConstructor
@ToString
public class InquireBankAccountTypesResponse {
    String result;
    AccountResponseHeader Header;
    REC[] REC;
}
