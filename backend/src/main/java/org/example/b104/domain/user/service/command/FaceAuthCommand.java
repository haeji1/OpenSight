package org.example.b104.domain.user.service.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class FaceAuthCommand {
    private MultipartFile requestImage;

}
