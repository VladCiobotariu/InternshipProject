package com.ozius.internship.project.controller;

import com.ozius.internship.project.entity.Category;
import com.ozius.internship.project.repository.CategoryRepository;
import com.ozius.internship.project.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryService categoryService, CategoryRepository categoryRepository) {
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
    }

//    @GetMapping("/categories")
//    public List<Category> getCategories() {
//        return categoryService.getCategories();
//    }

    @GetMapping("/categories")
    public ApiPaginationResponse<List<Category>> getCategoriesByItemsPerPage(@RequestParam(name = "itemsPerPage", defaultValue = "10") int itemsPerPage,
                                                                             @RequestParam(name = "page", defaultValue = "1") int page) {
        Page<Category> categoryPage = categoryService.getCategoriesByItemsPerPage(page-1, itemsPerPage);
        List<Category> categories = categoryPage.getContent();
        int totalElements = (int) categoryPage.getTotalElements();
        return new ApiPaginationResponse<>(page, itemsPerPage, totalElements, categories);
    }

    @GetMapping("/categories/categoryNames")
    public ResponseEntity<List<String>> getAllCategoryNames() {
        List<Category> categoryList = categoryRepository.findAll();
        List<String> categoryNames = categoryList.stream()
                .map(Category::getName)
                .collect(Collectors.toList());

        return ResponseEntity.ok(categoryNames);
    }

    @PostMapping("/categories")
    public ResponseEntity<Object> createCategory(@RequestBody Category category) {

        Category createdCategory = categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);

    }

}
