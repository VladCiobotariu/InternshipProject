package com.ozius.internship.project;

import com.ozius.internship.project.entity.Category;
import com.ozius.internship.project.entity.Product;
import jakarta.persistence.EntityManager;

public class TestDataCreator {


    public static void createBaseData(EntityManager em) {
        createCategoriesBaseData(em);
        createProductsBaseData(em);
    }


    public static void createProductsBaseData(EntityManager em) {
        //create couple of products for test data bootstrap reasons.
    }

    public static void createCategoriesBaseData(EntityManager em) {
        createCategory("Legume", "/legume", em);
        createCategory("Fructe", "/fructe", em);
        createCategory("Verdeturi", "/verdeturi", em);
    }

    public static Category createCategory(String name, String description, EntityManager em) {
        Category category = new Category(name, description);
        em.persist(category);
        return category;
    }

    public static Product createProduct(String name, String description, EntityManager em) {
        return null
    }
}
