package com.example.internesempla.service;

import com.example.internesempla.dto.AuthenticationRequest;
import com.example.internesempla.dto.FileDto;
import com.example.internesempla.entity.StorageFileEntity;
import com.example.internesempla.entity.UserEntity;
import com.example.internesempla.repository.StorageFileRepository;
import com.example.internesempla.repository.UserRepository;
import io.minio.*;
import io.minio.messages.Item;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MinioService {
    private final MinioClient minioClient;
    private final StorageFileRepository storageFileRepository;
    private final UserRepository userRepository;

    public MinioService(MinioClient minioClient, StorageFileRepository storageFileRepository, UserRepository userRepository) {
        this.minioClient = minioClient;
        this.storageFileRepository = storageFileRepository;
        this.userRepository = userRepository;
    }

    @Value("${application.minio.bucket.name}")
    private String bucketName;

    public List<FileDto> getListObjects() {
        List<FileDto> objects = new ArrayList<>();
        try {
            Iterable<Result<Item>> result = minioClient.listObjects(ListObjectsArgs.builder()
                    .bucket(bucketName)
                    .recursive(true)
                    .build());
            for (Result<Item> item : result) {
                objects.add(new FileDto.Builder()
                        .setSize(item.get().size())
                        .setFilename(item.get().objectName())
                        .setUrl(getPreSignedUrl(item.get().objectName()))
                        .build());
            }
            return objects;
        } catch (Exception ignored) {
        }

        return objects;
    }

    public FileDto uploadFile(FileDto request) {
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(request.getFile().getOriginalFilename())
                    .stream(request.getFile().getInputStream(), request.getFile().getSize(), -1)
                    .build());
        } catch (Exception ignored) {
        }
        StorageFileEntity storageFile = new StorageFileEntity();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        storageFile.setName(request.getFile().getOriginalFilename());
        storageFile.setSize((int) request.getFile().getSize());
        storageFile.setMimeType(request.getFile().getContentType());
        storageFile.setPath(getPreSignedUrl(request.getFile().getOriginalFilename()));
        storageFile.setCreatedBy((UserEntity) auth.getPrincipal());
        storageFile.setCreatedDate(LocalDate.now());
        var exist = storageFileRepository.findByPath(storageFile.getPath());
        if (exist.isEmpty()){
            storageFileRepository.save(storageFile);
        }

        return new FileDto.Builder()
                .setTitle(request.getTitle())
                .setSize(request.getFile().getSize())
                .setUrl(getPreSignedUrl(request.getFile().getOriginalFilename()))
                .setFilename(request.getFile().getOriginalFilename())
                .build();
    }

    public InputStream getObject(String filename) {
        InputStream stream;
        try {
            stream = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(filename)
                    .build());
        } catch (Exception e) {
            return null;
        }

        return stream;
    }

    @Transactional
    public void deleteFile(String filename) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(filename)
                    .build());
            storageFileRepository.deleteByName(filename);
        } catch (Exception ignored) {
        }
    }

    private String getPreSignedUrl(String filename) {
        return "http://localhost:9090/api/file/".concat(filename);
    }

}
