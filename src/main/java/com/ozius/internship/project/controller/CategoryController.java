package com.ozius.internship.project.controller;

import com.ozius.internship.project.entity.Category;
import com.ozius.internship.project.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class CategoryController {
    private final CategoryService categoryService;
    private final ImageController imageController;

    public CategoryController(CategoryService categoryService, ImageController imageController) {
        this.categoryService = categoryService;
        this.imageController = imageController;
    }

    @GetMapping("/categories")
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }

    // TODO - this is not correct, only made to have an idea
    @PostMapping("/categories")
    public ResponseEntity<Object> createCategory(@PathVariable String categoryName,
                                                 @RequestParam String imageUrl) {
        String imageName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);

        MultipartFile imageFile = null;
        ResponseEntity<String> uploadResponse = imageController.uploadImage(imageFile);

        if(uploadResponse.getStatusCode() == HttpStatus.CREATED) {
            String uploadedUrl = uploadResponse.getBody();

            Category newCat = categoryService.createCategory(categoryName, uploadedUrl);
            return ResponseEntity.status(HttpStatus.CREATED).body(newCat);
        }
        else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
