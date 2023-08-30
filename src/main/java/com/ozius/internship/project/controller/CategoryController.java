package com.ozius.internship.project.controller;

import com.ozius.internship.project.entity.Category;
import com.ozius.internship.project.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/categories")
    public ResponseEntity<Object> createCategory(@PathVariable String categoryName,
                                                 @PathVariable String imageUrl) {

        Category newCat = categoryService.createCategory(categoryName, imageUrl);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCat);

    }

}
