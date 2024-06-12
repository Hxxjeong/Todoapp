package com.example.todoapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final S3Client s3Client;
    private final String bucketName = "s3-bucket0610";

    // 파일 업로드
    public void uploadFile(MultipartFile file, String key) throws IOException {
//        String key = file.getOriginalFilename();  // S3에만 업로드하는 경우

        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)   // 경로를 포함한 파일 이름
                        .build(),
                RequestBody.fromBytes(file.getBytes())
        );
    }

    // 파일 다운로드
    public InputStream downloadFile(String key) {
        return s3Client.getObject(
                GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build()
        );
    }
}
