package com.ozius.internship.project.infra.images.controller;

import com.ozius.internship.project.infra.images.service.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImageHandlingController {

    private final ImageService imageService;

    public ImageHandlingController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/images")
    public String upload(@RequestParam("file") MultipartFile file) {
        return imageService.upload(file);
    }

    @GetMapping("/images/{imageName}")
    public ResponseEntity<byte[]> retrieve(@PathVariable String imageName) {
        return imageService.retrieve(imageName);
    }

}
