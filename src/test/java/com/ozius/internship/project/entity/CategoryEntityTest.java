package com.ozius.internship.project.entity;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryEntityTest extends EntityBaseTest {

    private JpaRepository<Category, Long> categoryRepository;

    @Override
    public void createTestData(EntityManager em) {
        this.categoryRepository = new SimpleJpaRepository<>(Category.class, emb);
    }

    @Test
    public void category_is_created() {
        // ----Act
        doTransaction(em -> {
            Category category = new Category("cafea", "/cafea");
            em.persist(category);
        });

        // ----Assert
        Category persistedCategory = categoryRepository.findAll().get(0);
        assertThat(persistedCategory.getName()).isEqualTo("cafea");
        assertThat(persistedCategory.getImageName()).isEqualTo("/cafea");
    }

    @Test
    public void category_is_created_v2() {
        // ----Act
        doTransaction(em -> {
            Category category = new Category("cafea", "/cafea");
            em.persist(category);
        });
        emb.clear();

        // ----Assert
        Category category = categoryRepository.findAll().get(0);
        assertThat(category.getName()).isEqualTo("cafea");
        assertThat(category.getImageName()).isEqualTo("/cafea");

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
            Category category = categoryRepository.findAll().get(0);
            Category categoryToModify = em.merge(category);
            categoryToModify.setImageName("/legumeDeLaUpdate");
        });
        emb.clear();

        // ----Assert
        Category persistedCategory = categoryRepository.findAll().get(0);
        assertThat(persistedCategory).isNotNull();
        assertThat(persistedCategory.getImageName()).isEqualTo("/legumeDeLaUpdate");
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
            Category category = categoryRepository.findAll().get(0);
            Category categoryToRemove = em.merge(category);
            em.remove(categoryToRemove);
        });
        emb.clear();

        // ----Assert
        List<Category> categories = categoryRepository.findAll();
        Category assertedCategory = categories.isEmpty() ? null : categories.get(0);

        assertThat(assertedCategory).isNull();
        assertThat(categoryRepository.findAll()).isEmpty();
    }
}
