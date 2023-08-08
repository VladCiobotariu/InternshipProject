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
    //TODO this is a duplicate of category_is_created() test,  probably a mistake due to migration from previous version. Please cleanup.
    public void category_is_created_v2() {
        // ----Act
        doTransaction(em -> {
            Category category = new Category("cafea", "/cafea");
            em.persist(category);
        });

        // ----Assert
        Category category = entityFinder.getTheOne(Category.class);
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
            EntityFinder entityFinder = new EntityFinder(em);
            Category category = entityFinder.getTheOne(Category.class);
            Category categoryToModify = em.merge(category); //TODO is this needed?
            categoryToModify.updateImage("/legumeDeLaUpdate");
        });

        // ----Assert
        Category persistedCategory = entityFinder.getTheOne(Category.class);
        assertThat(persistedCategory).isNotNull();  // TODO no delete/cascade delete operations are possible in this test, Not null assert not required.
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
            EntityFinder entityFinder = new EntityFinder(em);
            Category category = entityFinder.getTheOne(Category.class);
            Category categoryToRemove = em.merge(category);
            em.remove(categoryToRemove);
        });

        // ----Assert
        List<Category> categories = entityFinder.findAll(Category.class);

        //TODO instead of these 2 asserts is better to assert categories is empty.
        Category assertedCategory = categories.isEmpty() ? null : categories.get(0);
        assertThat(assertedCategory).isNull();

        //TODO this assert will never fail, in case categories are empty, assertedCategory will be null and above assert will throw an exception.
        assertThat(entityFinder.findAll(Category.class)).isEmpty();
    }
}
