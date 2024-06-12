package com.example.todoapp.service;

import com.example.todoapp.entity.FileEntity;
import com.example.todoapp.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;

    @Transactional
    public FileEntity saveFileMetaData(String uuid, String path, String originalFilename, long size, String mimeType) {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setUuid(uuid);
        fileEntity.setPath(path);
        fileEntity.setOriginalFilename(originalFilename);
        fileEntity.setSize(size);
        fileEntity.setMimeType(mimeType);

        return fileRepository.save(fileEntity);
    }

    public Optional<FileEntity> getFileMetaData(String uuid) {
        return fileRepository.findByUuid(uuid);
    }
}
