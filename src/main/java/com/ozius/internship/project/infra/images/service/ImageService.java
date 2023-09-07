package com.ozius.internship.project.infra.images.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    public ResponseEntity<String> upload(MultipartFile file);

    public ResponseEntity<byte[]> retrieve(@PathVariable String imageName);
}
