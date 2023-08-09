package com.ozius.internship.project.entity;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryEntityTest extends EntityBaseTest {

    @Override
    public void createTestData(EntityManager em) {
    }

    @Test
    public void category_is_created() {
        // ----Act
        doTransaction(em -> {
            Category category = new Category("cafea", "/cafea");
            em.persist(category);
        });

        // ----Assert
        Category persistedCategory = entityFinder.getTheOne(Category.class);
        assertThat(persistedCategory.getName()).isEqualTo("cafea");
        assertThat(persistedCategory.getImageName()).isEqualTo("/cafea");
    }

    @Test
    public void category_is_updated() {
        // ----Arrange
        doTransaction(em -> {
            Category category = new Category("legume", "/legume");
            em.persist(category);
        });

        // ----Arrange
        // need to get the object again because category is now detached
        doTransaction(em -> {
            EntityFinder entityFinder = new EntityFinder(em);
            Category category = entityFinder.getTheOne(Category.class);
            category.updateCategory("vegetables", "/vegetables");
        });

        // ----Assert
        Category persistedCategory = entityFinder.getTheOne(Category.class);
        assertThat(persistedCategory.getName()).isEqualTo("vegetables");
        assertThat(persistedCategory.getImageName()).isEqualTo("/vegetables");
    }

    @Test
    public void category_is_removed() {
        // ----Arrange
        doTransaction(em -> {
            Category category = new Category("legume", "/legume");
            em.persist(category);
        });

        // ----Act
        doTransaction(em -> {
            EntityFinder entityFinder = new EntityFinder(em);
            Category category = entityFinder.getTheOne(Category.class);
            em.remove(category);
        });

        // ----Assert
        List<Category> categories = entityFinder.findAll(Category.class);
        assertThat(categories.isEmpty());
    }
}
