package com.ozius.internship.project.service;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    public String determineContentType(String imageName) {
        if (imageName.endsWith(".jpg") || imageName.endsWith(".jpeg")) {
            return MediaType.IMAGE_JPEG_VALUE;
        } else if (imageName.endsWith(".png")) {
            return MediaType.IMAGE_PNG_VALUE;
        } else if (imageName.endsWith(".svg")) {
            return "image/svg+xml";
        }
        return imageName;
    }

}
