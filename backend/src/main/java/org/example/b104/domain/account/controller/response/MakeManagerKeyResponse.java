package org.example.b104.domain.account.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class MakeManagerKeyResponse {
    String result;
    String managerId;
    String apiKey;
}
