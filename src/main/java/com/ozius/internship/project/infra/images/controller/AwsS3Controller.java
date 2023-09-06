package com.ozius.internship.project.infra.images.controller;

import com.ozius.internship.project.infra.images.service.AwsS3Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AwsS3Controller {
    private final AwsS3Service awsS3Service;

    public AwsS3Controller(AwsS3Service awsS3Service) {
        this.awsS3Service = awsS3Service;
    }

    @PostMapping("/images-s3")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        return awsS3Service.upload(file);
    }


    @GetMapping("/images-s3/{imageName}")
    public ResponseEntity<byte[]> retrieve(@PathVariable String imageName) {
        return awsS3Service.retrieve(imageName);
    }
}
