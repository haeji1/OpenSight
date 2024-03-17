package org.example.b104.domain.account.controller.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MakeManagerKeyRequest {
    String managerId;
    @Builder
    public MakeManagerKeyRequest(String managerId) {
        this.managerId = managerId;
    }
}
