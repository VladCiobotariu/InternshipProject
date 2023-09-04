package com.ozius.internship.project.service;

import com.ozius.internship.project.entity.Category;
import com.ozius.internship.project.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

//    public List<Category> getCategories() {
//        return categoryRepository.findAll();
//    }

    public List<Category> getCategoriesByItemsPerPage(int itemsPerPage, int page) {
        List<Category> categories = categoryRepository.findAll();

        int startIndex = (page - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, categories.size());

        return categories.subList(startIndex, endIndex);
    }


    public Category createCategory(Category category) {
        categoryRepository.save(category);
        return category;
    }
}
