package com.ozius.internship.project.entity;

import com.ozius.internship.project.JpaBaseEntity;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryEntityTest extends JpaBaseEntity {

    @Override
    public void createTestData(EntityManager em) {
    }

    @Test
    void category_is_created() {
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
    void category_is_updated() {
        // ----Arrange
        doTransaction(em -> {
            Category category = new Category("legume", "/legume");
            em.persist(category);
        });

        // ----Arrange
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
    void category_is_removed() {
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
        assertThat(entityFinder.findAll(Category.class).isEmpty());
    }

}
