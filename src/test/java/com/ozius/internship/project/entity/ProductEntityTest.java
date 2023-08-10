package com.ozius.internship.project.entity;

import com.ozius.internship.project.TestDataCreator;
import com.ozius.internship.project.entity.seller.Seller;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

import static com.ozius.internship.project.TestDataCreator.Categories.category1;
import static com.ozius.internship.project.TestDataCreator.Sellers.seller2;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductEntityTest extends EntityBaseTest {

    @Override
    public void createTestData(EntityManager em) {
        TestDataCreator.createCategoriesBaseData(em);
        TestDataCreator.createSellerBaseData(em);
    }

    @Test
    public void product_is_created() {
         //----Act
        doTransaction(em -> {
            Category cat = em.merge(category1);
            Seller seller = em.merge(seller2);
            Product product = new Product("vinete", "descriereVinete", "/vinete", 5F, cat, seller);
            em.persist(product);
        });

        //----Assert
        Product persistedProduct = entityFinder.getTheOne(Product.class);
        assertThat(persistedProduct).isNotNull();
        assertThat(persistedProduct.getName()).isEqualTo("vinete");
        assertThat(persistedProduct.getPrice()).isEqualTo(5);
        assertThat(persistedProduct.getCategory()).isEqualTo(category1);
        assertThat(persistedProduct.getSeller()).isEqualTo(seller2);
    }

    @Test
    public void product_is_updated() {
        //----Arrange
        doTransaction(em -> {
            Category cat = em.merge(category1);
            Seller seller = em.merge(seller2);
            Product product = new Product("vinete", "descriereVinete", "/vinete", 5F, cat, seller);
            em.persist(product);
        });

        //----Act
        doTransaction(em -> {
            EntityFinder entityFinder = new EntityFinder(em);
            Product pr = entityFinder.getTheOne(Product.class);
            pr.updateProduct("vinete_schimbate", "vinete mari", pr.getImageName(), 6F, pr.getCategory(), pr.getSeller());
        });

        //----Assert
        Product persistedProduct = entityFinder.getTheOne(Product.class);
        assertThat(persistedProduct).isNotNull();
        assertThat(persistedProduct.getName()).isEqualTo("vinete_schimbate");
        assertThat(persistedProduct.getDescription()).isEqualTo("vinete mari");
        assertThat(persistedProduct.getImageName()).isEqualTo("/vinete");
        assertThat(persistedProduct.getPrice()).isEqualTo(6F);
        assertThat(persistedProduct.getCategory()).isEqualTo(category1);
        assertThat(persistedProduct.getSeller()).isEqualTo(seller2);
    }

    @Test
    public void product_is_deleted() {
        //----Arrange
        doTransaction(em -> {
            Category cat = em.merge(category1);
            Seller seller = em.merge(seller2);
            Product product = new Product("vinete", "descriereVinete", "/vinete", 5F, cat, seller);
            em.persist(product);
        });

        //----Act
        doTransaction(em -> {
            EntityFinder entityFinder = new EntityFinder(em);
            Product product = entityFinder.getTheOne(Product.class);
            em.remove(product);
        });

        //----Assert
        assertThat(entityFinder.findAll(Product.class)).isEmpty();
    }

}
