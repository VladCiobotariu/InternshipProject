package com.ozius.internship.project.entity;

import com.ozius.internship.project.TestDataCreatorErika;
import com.ozius.internship.project.entity.seller.Seller;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductEntityTest extends EntityBaseTest {

    @Override
    public void createTestData(EntityManager em) {
        TestDataCreatorErika.createCategoriesBaseData(em);
        TestDataCreatorErika.createSellerBaseData(em);
    }

    @Test
    public void product_is_created() {
         //----Act
        doTransaction(em -> {
            Category cat = em.merge(TestDataCreatorErika.Categories.category1);
            Seller seller = em.merge(TestDataCreatorErika.Sellers.seller2);
            Product product = new Product("vinete", "descriereVinete", "/vinete", 5F, cat, seller);
            em.persist(product);
        });

        //----Assert
        Product persistedProduct = entityFinder.getTheOne(Product.class);
        assertThat(persistedProduct).isNotNull();
        assertThat(persistedProduct.getName()).isEqualTo("vinete");
        assertThat(persistedProduct.getPrice()).isEqualTo(5);
        assertThat(persistedProduct.getCategory()).isEqualTo(TestDataCreatorErika.Categories.category1);
        assertThat(persistedProduct.getSeller()).isEqualTo(TestDataCreatorErika.Sellers.seller2);
    }

    @Test
    public void product_is_updated() {
        //----Arrange
        doTransaction(em -> {
            Category cat = em.merge(TestDataCreatorErika.Categories.category1);
            Seller seller = em.merge(TestDataCreatorErika.Sellers.seller2);
            Product product = new Product("vinete", "descriereVinete", "/vinete", 5F, cat, seller);
            em.persist(product);
        });

        //----Act
        doTransaction(em -> {
            EntityFinder entityFinder = new EntityFinder(em);
            Product product = entityFinder.getTheOne(Product.class);
            Product productToUpdate = em.merge(product);
            productToUpdate.updateDescription("vinete mari");
        });

        //----Assert
        Product persistedProduct = entityFinder.getTheOne(Product.class);
        assertThat(persistedProduct).isNotNull();
        assertThat(persistedProduct.getDescription()).isEqualTo("vinete mari");
    }

    @Test
    public void product_is_deleted() {
        //----Arrange
        doTransaction(em -> {
            Category cat = em.merge(TestDataCreatorErika.Categories.category1);
            Seller seller = em.merge(TestDataCreatorErika.Sellers.seller2);
            Product product = new Product("vinete", "descriereVinete", "/vinete", 5F, cat, seller);
            em.persist(product);
        });

        //----Act
        doTransaction(em -> {
            EntityFinder entityFinder = new EntityFinder(em);
            Product product = entityFinder.getTheOne(Product.class);
            Product productToRemove = em.merge(product);
            em.remove(productToRemove);
        });

        //----Assert
        List<Product> products = entityFinder.findAll(Product.class);
        Product assertedProduct = products.isEmpty() ? null : products.get(0);

        assertThat(entityFinder.findAll(Product.class)).isEmpty();
        assertThat(assertedProduct).isNull();

    }

}
