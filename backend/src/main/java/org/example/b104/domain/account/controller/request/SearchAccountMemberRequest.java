package org.example.b104.domain.account.controller.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class SearchAccountMemberRequest {
    private String userId;
    private String apiKey;
}
