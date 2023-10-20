package com.ozius.internship.project.infra.images.controller;

import com.ozius.internship.project.entity.exception.FileDownloadException;
import com.ozius.internship.project.infra.images.service.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

@RestController
public class ImageHandlingController {

    private final ImageService imageService;

    public ImageHandlingController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/images")
    public String upload(@RequestParam("file") MultipartFile file) throws FileNotFoundException {
        return imageService.upload(file);
    }

    @GetMapping("/images/{imageName}")
    public Object retrieve(@PathVariable String imageName) throws FileDownloadException {
        return imageService.retrieve(imageName);
    }

}
