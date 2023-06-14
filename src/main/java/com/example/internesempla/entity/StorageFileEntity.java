package com.example.internesempla.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "storage_file")
public class StorageFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer size;
    private String mimeType;
    private String path;
    @ManyToOne()
    @JoinColumn(name = "created_by", referencedColumnName = "login")
    private UserEntity createdBy;
    private LocalDate createdDate;

    public StorageFileEntity() {
    }

    public StorageFileEntity(String name, Integer size, String mimeType, String path, UserEntity createdBy, LocalDate createdDate) {
        this.name = name;
        this.size = size;
        this.mimeType = mimeType;
        this.path = path;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
    }

    public StorageFileEntity(Integer id, String name, Integer size, String mimeType, String path, UserEntity createdBy, LocalDate createdDate) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.mimeType = mimeType;
        this.path = path;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserEntity createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }
}
