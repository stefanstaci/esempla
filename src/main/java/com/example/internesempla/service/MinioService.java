package com.example.internesempla.service;

import com.example.internesempla.dto.FileDto;
import io.minio.*;
import io.minio.messages.Item;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class MinioService {
    private final MinioClient minioClient;

    public MinioService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    @Value("${minio.bucket.name}")
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

    public void deleteFile(String filename) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(filename)
                    .build());
        } catch (Exception ignored) {
        }
    }

    private String getPreSignedUrl(String filename) {
        return "http://localhost:9090/api/file/".concat(filename);
    }

}
