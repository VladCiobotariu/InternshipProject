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

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

//    @GetMapping("/categories")
//    public List<Category> getCategories() {
//        return categoryService.getCategories();
//    }

    @GetMapping("/categories")
    public ApiPaginationResponse<List<Category>> getCategoriesByItemsPerPage(
            @RequestParam(name = "itemsPerPage", defaultValue = "10") int itemsPerPage,
            @RequestParam(name = "page", defaultValue = "1") int page
    ) {
        List<Category> categories = categoryService.getCategoriesByItemsPerPage(itemsPerPage, page);
        return new ApiPaginationResponse<>(page, itemsPerPage, categories);
    }

    @PostMapping("/categories")
    public ResponseEntity<Object> createCategory(@RequestBody Category category) {

        Category createdCategory = categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);

    }

}
