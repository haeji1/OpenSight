package org.example.b104.domain.user.controller.request;

import lombok.Builder;
import lombok.Data;
import org.example.b104.domain.user.service.command.FaceAuthCommand;
import org.example.b104.domain.user.service.command.FaceLoginCommand;
import org.springframework.web.multipart.MultipartFile;
@Data
@Builder
public class FaceAuthRequest {
    MultipartFile requestImage;
    public FaceAuthCommand toFaceAuthCommand() {
        return FaceAuthCommand.builder()
                .requestImage(requestImage)
                .build();
    }

}
