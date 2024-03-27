package org.example.b104.domain.user.controller.request;


import lombok.Builder;
import lombok.Data;
import org.example.b104.domain.user.service.command.FaceLoginCommand;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class FaceLoginRequest {
    MultipartFile requestImage;

    public FaceLoginCommand toFaceLoginCommand() {
        return FaceLoginCommand.builder()
                .requestImage(requestImage)
                .build();
    }
}
