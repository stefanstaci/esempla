package com.example.internesempla.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    private final ApplicationProprieties applicationProprieties;

    public ApplicationConfiguration(ApplicationProprieties applicationProprieties) {
        this.applicationProprieties = applicationProprieties;
    }

    @Bean
    public MinioClient minioClient() {
        return new MinioClient.Builder()
                .credentials(applicationProprieties.getAccessKey(), applicationProprieties.getSecretKey())
                .endpoint(applicationProprieties.getMinioUrl())
                .build();
    }
}

