package com.ozius.internship.project.infra.images.controller;

import com.ozius.internship.project.infra.images.service.LocalDiskImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class LocalDiskImageController {

    private final LocalDiskImageService localDiskImageService;

    public LocalDiskImageController(LocalDiskImageService localDiskImageService) {
        this.localDiskImageService = localDiskImageService;
    }

    @PostMapping("/images-dev")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        return localDiskImageService.upload(file);
    }

    @GetMapping("/images-dev/{imageName}")
    public ResponseEntity<byte[]> retrieve(@PathVariable String imageName) {
        return localDiskImageService.retrieve(imageName);
    }

}
