package com.ozius.internship.project.entity;

import com.ozius.internship.project.entity.exception.IllegalDuplicateName;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

import static com.ozius.internship.project.entity.Category.listOfCategoryNames;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
            listOfCategoryNames.remove(category.getName());
        });

        // ----Assert
        assertThat(entityFinder.findAll(Category.class).isEmpty());
    }

    @Test
    public void category_added_with_already_existing_name_throws_exception() {
        // ----Arrange
        doTransaction(em -> {
            Category category = new Category("legume", "/legume");
            em.persist(category);
        });

        // ----Arrange
        Exception exception = doTransaction(em -> {
            return assertThrows(IllegalDuplicateName.class, () -> {
                Category categoryAdded = new Category("legume", "/newLegume");
                em.persist(categoryAdded);
            });
        });

        // ----Assert
        assertTrue(exception.getMessage().contains("A category with this name already exists!"));
    }

    @Test
    public void category_updated_with_already_existing_name_throws_exception() {
        // ----Arrange
        Category categoryToUpdate = doTransaction(em -> {
            Category category1 = new Category("legume", "/legume");
            Category category2 = new Category("dulciuri", "/dulciuri");
            em.persist(category1);
            em.persist(category2);
            return category2;
        });

        // ----Arrange
        Exception exception = doTransaction(em -> {
            return assertThrows(IllegalDuplicateName.class, () -> {
                categoryToUpdate.updateCategory("legume", "/vegetables");
            });
        });

        // ----Assert
        assertTrue(exception.getMessage().contains("Seller already has a product with this name!"));
    }
}
