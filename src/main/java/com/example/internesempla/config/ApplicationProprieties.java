package com.example.internesempla.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("minio")
public class ApplicationProprieties {
    private String accessKey;
    private String secretKey;
    private String minioUrl;
    private String bucketName;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getMinioUrl() {
        return minioUrl;
    }

    public void setMinioUrl(String minioUrl) {
        this.minioUrl = minioUrl;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}

