package com.example.internesempla.service;

import com.example.internesempla.config.ApplicationProprieties;
import com.example.internesempla.controller.AuthenticationController;
import com.example.internesempla.dto.FileDto;
import com.example.internesempla.entity.StorageFileEntity;
import com.example.internesempla.entity.UserEntity;
import com.example.internesempla.enumeration.RoleEnum;
import com.example.internesempla.repository.StorageFileRepository;
import com.example.internesempla.repository.UserRepository;
import com.example.internesempla.repository.UserReservationRepository;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.NoPermissionException;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MinioService {
    private final MinioClient minioClient;
    private final StorageFileRepository storageFileRepository;
    private final UserRepository userRepository;
    private final ApplicationProprieties applicationProprieties;
    private final MailService mailService;
    private final UserReservationRepository userReservationRepository;
    private final ReservationService reservationService;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);


    public MinioService(MinioClient minioClient, StorageFileRepository storageFileRepository, UserRepository userRepository, ApplicationProprieties applicationProprieties, MailService mailService, UserReservationRepository userReservationRepository, ReservationService reservationService) {
        this.minioClient = minioClient;
        this.storageFileRepository = storageFileRepository;
        this.userRepository = userRepository;
        this.applicationProprieties = applicationProprieties;
        this.mailService = mailService;
        this.userReservationRepository = userReservationRepository;
        this.reservationService = reservationService;
    }

    public List<FileDto> getListObjects() {
        List<FileDto> objects = new ArrayList<>();
        try {
            Iterable<Result<Item>> result = minioClient.listObjects(ListObjectsArgs.builder()
                    .bucket(applicationProprieties.getBucketName())
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
                    .bucket(applicationProprieties.getBucketName())
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

        if (exist.isEmpty()) {
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getPrincipal().equals(userRepository.findByRoles(RoleEnum.ADMIN));
        boolean havePermission = auth.getPrincipal().equals(storageFileRepository.findByName(filename).getCreatedBy());
        try {
            if (havePermission || isAdmin) {
                stream = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(applicationProprieties.getBucketName())
                    .object(filename)
                    .build());
            } else {
                logger.info("not permission for downloading file");
                throw new NoPermissionException();
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }

        return stream;
    }

    @Transactional
    public void deleteFile(String filename) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            boolean isAdmin = auth.getPrincipal().equals(userRepository.findByRoles(RoleEnum.ADMIN));
            boolean havePermission = auth.getPrincipal().equals(storageFileRepository.findByName(filename).getCreatedBy());
            if (havePermission || isAdmin) {
                minioClient.removeObject(RemoveObjectArgs.builder()
                        .bucket(applicationProprieties.getBucketName())
                        .object(filename)
                        .build());
                storageFileRepository.deleteByName(filename);
                logger.info("file deleted");
            } else {
                logger.info("not permission for deleting file");
                throw new NoPermissionException();
            }
        } catch (ErrorResponseException | InsufficientDataException | InternalException |
                 InvalidKeyException | InvalidResponseException | IOException | NoSuchAlgorithmException |
                 ServerException | XmlParserException e) {
            System.out.println(e.getMessage());
        } catch (NoPermissionException e) {
            throw new RuntimeException(e);
        }
    }

    private String getPreSignedUrl(String filename) {
        return "http://localhost:8080/api/file/".concat(filename);
    }

}
