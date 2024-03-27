package org.example.b104.domain.user.service.command;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class UpdateFaceCommand {
    String userId;

    MultipartFile requestImage;
}
