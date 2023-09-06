//package com.ozius.internship.project.controller;
//
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.core.io.Resource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.beans.factory.annotation.Value;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//@RestController
//public class ImageService {
//
//    @Value("${upload.path}")
//    private String imageUploadPath;
//
//    public String determineContentType(String imageName) {
//        if (imageName.endsWith(".jpg") || imageName.endsWith(".jpeg")) {
//            return MediaType.IMAGE_JPEG_VALUE;
//        } else if (imageName.endsWith(".png")) {
//            return MediaType.IMAGE_PNG_VALUE;
//        } else if (imageName.endsWith(".svg")) {
//            return "image/svg+xml";
//        }
//        return imageName;
//    }
//
//    @PostMapping("/images")
//    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
//        try {
//            String fileName = file.getOriginalFilename();
//            File imageFile = new File(imageUploadPath + fileName);
//            try (FileOutputStream fos = new FileOutputStream(imageFile)) {
//                fos.write(file.getBytes());
//            }
//            String imageUrl = "/images/" + fileName;
//            return ResponseEntity.status(HttpStatus.CREATED).body(imageUrl);
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image.");
//        }
//    }
//
//    @GetMapping("/images/{imageName}")
//    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) {
//        try {
//            Path imagePath = Paths.get(imageUploadPath, imageName);
//            Resource resource = new FileSystemResource(imagePath);
//            byte[] imageBytes = resource.getInputStream().readAllBytes();
//
//            String contentType = determineContentType(imageName);
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.valueOf(contentType));
//
//            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//    }
//}
