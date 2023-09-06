package com.ozius.internship.project.infra.images.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;

@Service
public class AwsS3Service implements ImageService {

    @Override
    public ResponseEntity<String> upload(MultipartFile file) {
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<byte[]> retrieve(String imageName) {
        return ResponseEntity.notFound().build();
    }
}
