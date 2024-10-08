package org.example.b104.domain.account.controller.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@AllArgsConstructor
@ToString
public class SearchAccountMemberResponse {
    String result;
    String userId;
    String userName;
    String institutionCode;
    String userKey;
    String created;
    String modified;
    String now;
}
