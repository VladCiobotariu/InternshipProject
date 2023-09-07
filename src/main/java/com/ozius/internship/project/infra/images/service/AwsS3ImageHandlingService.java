package com.ozius.internship.project.infra.images.service;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;

public class AwsS3ImageHandlingService implements ImageService {

    @Override
    public String upload(MultipartFile file) {
        return null;
    }

    @Override
    public ResponseEntity<byte[]> retrieve(String imageName) {
        return ResponseEntity.notFound().build();
    }
}
