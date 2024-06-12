package com.example.todoapp.controller;

import com.example.todoapp.entity.FileEntity;
import com.example.todoapp.service.FileService;
import com.example.todoapp.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/s3")
@RequiredArgsConstructor
public class S3Controller {
    private final S3Service s3Service;

    // S3에만 작업할 때
    // 파일 업로드
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            s3Service.uploadFile(file);

            return ResponseEntity.status(HttpStatus.OK).body("Success file upload!");
        }
        catch (Exception e) {
            return ResponseEntity.status(500).body("Failed file upload.");
        }
    }

    // 파일 다운로드
    @GetMapping("/download/{key}")
    public ResponseEntity<StreamingResponseBody> downloadFile(@PathVariable("key") String key) {
        try {
            InputStream inputStream = s3Service.downloadFile(key);
            StreamingResponseBody responseBody = outputStream -> {
                byte[] bytes = new byte[1024];
                int bytesRead;
                while((bytesRead = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, bytesRead);    // 읽어온 길이만큼 쓰기
                }
                inputStream.close();
            };

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + key + "\"")
                    .body(responseBody);
        }
        catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
