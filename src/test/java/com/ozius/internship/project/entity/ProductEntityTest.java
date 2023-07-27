package com.ozius.internship.project.entity;

import com.ozius.internship.project.TestDataCreator;
;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductEntityTest extends EntityBaseTest {

    private JpaRepository<Product, Long> productRepository;

    @Override
    public void createTestData(EntityManager em) {
        this.productRepository = new SimpleJpaRepository<>(Product.class, emb);
        TestDataCreator.createCategoriesBaseData(em);
        TestDataCreator.createSellerBaseData(em);

        new SimpleJpaRepository<>(Category.class, em).findAll().stream().forEach(cat -> System.out.println(cat));

    }

    @Test
    public void product_is_created() {
         //----Act
        doTransaction(em -> {
            Category cat = em.merge(TestDataCreator.Categories.category1);
            Seller seller = em.merge(TestDataCreator.Sellers.seller2);
            Product product = new Product("vinete", "descriereVinete", "/vinete", 5F, cat, seller);
            em.persist(product);
        });

        //----Assert
        Product persistedProduct = productRepository.findAll().get(0);
        assertThat(persistedProduct).isNotNull();
        assertThat(persistedProduct.getName()).isEqualTo("vinete");
        assertThat(persistedProduct.getPrice()).isEqualTo(5);
        assertThat(persistedProduct.getCategory().getId()).isEqualTo(TestDataCreator.Categories.category1.getId());
        assertThat(persistedProduct.getSellerInfo().getId()).isEqualTo(TestDataCreator.Sellers.seller2.getId());
        // same id but different objects!
    }

    @Test
    public void product_is_updated() {
        //----Arrange
        doTransaction(em -> {
            Category cat = em.merge(TestDataCreator.Categories.category1);
            Seller seller = em.merge(TestDataCreator.Sellers.seller2);
            Product product = new Product("vinete", "descriereVinete", "/vinete", 5F, cat, seller);
            em.persist(product);
        });

        //----Act
        doTransaction(em -> {
            Product product = productRepository.findAll().get(0);
            Product productToUpdate = em.merge(product);
            productToUpdate.setDescription("vinete mari");
        });
        emb.clear();
        //----Assert
        Product persistedProduct = productRepository.findAll().get(0);
        assertThat(persistedProduct).isNotNull();
        assertThat(persistedProduct.getDescription()).isEqualTo("vinete mari");
    }

    @Test
    public void product_is_deleted() {
        //----Arrange
        doTransaction(em -> {
            Category cat = em.merge(TestDataCreator.Categories.category1);
            Seller seller = em.merge(TestDataCreator.Sellers.seller2);
            Product product = new Product("vinete", "descriereVinete", "/vinete", 5F, cat, seller);
            em.persist(product);
        });

        //----Act
        doTransaction(em -> {
            Product product = productRepository.findAll().get(0);
            Product productToRemove = em.merge(product);
            em.remove(productToRemove);
        });

        //----Assert
        List<Product> products = productRepository.findAll();
        Product assertedProduct = products.isEmpty() ? null : products.get(0);

        assertThat(productRepository.findAll()).isEmpty();
        assertThat(assertedProduct).isNull();

    }

}
