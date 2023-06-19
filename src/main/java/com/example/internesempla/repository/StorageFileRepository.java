package com.example.internesempla.repository;

import com.example.internesempla.entity.StorageFileEntity;
import com.example.internesempla.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StorageFileRepository extends JpaRepository<StorageFileEntity, Integer> {
    void deleteByName(String name);
    Optional<StorageFileEntity> findByPath(String path);

    StorageFileEntity findByName(String filename);
}
