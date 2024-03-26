package org.example.b104.domain.user.controller.request;


import lombok.Data;
import org.example.b104.domain.user.service.command.UpdateFaceCommand;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateFaceRequest {
    MultipartFile requestImage;

    public UpdateFaceCommand toUpdateFaceCommand() {
        return UpdateFaceCommand.builder()
                .requestImage(requestImage)
                .build();
    }
}
