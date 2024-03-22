package org.example.b104.domain.file.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/file")
public class FileUploadController {

    private static final String UPLOAD_DIR = "C:\\Users\\SSAFY\\Pictures";

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "Please select a file to upload";
        }

        try {
            // 디렉토리가 없으면 생성
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // 파일을 지정된 디렉토리에 저장
            String fileName = file.getOriginalFilename();
            File destFile = new File(UPLOAD_DIR + File.separator + fileName);
            file.transferTo(destFile);

            return "File uploaded successfully";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to upload file";
        }
    }
}
