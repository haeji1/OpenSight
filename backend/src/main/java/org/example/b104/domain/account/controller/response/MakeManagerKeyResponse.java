package org.example.b104.domain.account.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MakeManagerKeyResponse {
    String result;
    String managerId;
    String apiKey;
    @Builder
    public MakeManagerKeyResponse(String result, String managerId, String apiKey) {
        this.result = result;
        this.managerId = managerId;
        this.apiKey = apiKey;
    }
}
