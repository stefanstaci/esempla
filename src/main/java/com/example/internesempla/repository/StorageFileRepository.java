package com.example.internesempla.repository;

import com.example.internesempla.entity.StorageFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageFileRepository extends JpaRepository<StorageFileEntity, Integer> {
    void deleteByPath(String path);

}
