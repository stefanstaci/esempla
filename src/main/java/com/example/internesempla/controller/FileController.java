package com.example.internesempla.controller;

import com.example.internesempla.dto.FileDto;
import com.example.internesempla.service.MinioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static org.springframework.web.servlet.HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE;

@RestController
@RequestMapping("/file")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private final MinioService minioService;

    public FileController(MinioService minioService) {
        this.minioService = minioService;
    }

    @GetMapping()
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Object> getFiles() {
        logger.info("the file information has been viewed");
        return ResponseEntity.ok(minioService.getListObjects());
    }

    @PostMapping( "/upload")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Object> upload(@ModelAttribute FileDto request) {
        logger.info("a file has been uploaded");
        return ResponseEntity.ok().body(minioService.uploadFile(request));
    }

    @GetMapping("/**")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Object> downloadFile(HttpServletRequest request) throws IOException {
        String pattern = (String) request.getAttribute(BEST_MATCHING_PATTERN_ATTRIBUTE);
        String filename = new AntPathMatcher().extractPathWithinPattern(pattern, request.getServletPath());
        logger.info("a file {} has been downloaded", filename);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(IOUtils.toByteArray(minioService.getObject(filename)));
    }

    @DeleteMapping( "/**")
    @SecurityRequirement(name = "JWT")
    public void deleteFile(HttpServletRequest request){
        String pattern = (String) request.getAttribute(BEST_MATCHING_PATTERN_ATTRIBUTE);
        String filename = new AntPathMatcher().extractPathWithinPattern(pattern, request.getServletPath());
        logger.info("a file {} has been deleted", filename);
        minioService.deleteFile(filename);
    }
}
