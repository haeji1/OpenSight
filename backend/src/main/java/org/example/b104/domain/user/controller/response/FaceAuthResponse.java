package org.example.b104.domain.user.controller.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FaceAuthResponse {
    String result;
    Long userId;

}
