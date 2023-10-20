package com.ozius.internship.project.infra.images.service;

import com.ozius.internship.project.entity.exception.FileDownloadException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

public interface ImageService {

    String upload(MultipartFile file) throws FileNotFoundException, FileUploadException;

    ResponseEntity<byte[]> retrieve(String imageName) throws FileDownloadException;

}
