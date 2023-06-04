package com.example.internesempla.dto;

import io.minio.DownloadObjectArgs;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public class FileDto implements Serializable {

    private static final long serialVersionUID = 232836038145089522L;

    private String title;

    @SuppressWarnings("java:S1948")
    private MultipartFile file;

    private String url;

    private Long size;

    private String filename;

    public FileDto() {
    }

    public FileDto(Builder builder) {
        this.title = builder.title;
        this.file = builder.file;
        this.url = builder.url;
        this.size = builder.size;
        this.filename = builder.filename;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public static class Builder {
        private String title;

        @SuppressWarnings("java:S1948")
        private MultipartFile file;

        private String url;

        private Long size;

        private String filename;

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setFile(MultipartFile file) {
            this.file = file;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setSize(Long size) {
            this.size = size;
            return this;
        }

        public Builder setFilename(String filename) {
            this.filename = filename;
            return this;
        }

        public FileDto build() {
            return new FileDto(this);
        }
    }
}

