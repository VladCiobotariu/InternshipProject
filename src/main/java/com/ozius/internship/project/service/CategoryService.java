package com.ozius.internship.project.service;

import com.ozius.internship.project.entity.Category;
import com.ozius.internship.project.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Page<Category> getCategoriesByItemsPerPage(int page, int itemsPerPage) {
        Pageable pageable = PageRequest.of(page, itemsPerPage);
        return categoryRepository.findAll(pageable);
    }


    public Category createCategory(Category category) {
        categoryRepository.save(category);
        return category;
    }
}
