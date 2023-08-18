package com.ozius.internship.project.entity;

import com.ozius.internship.project.TestDataCreator;
import com.ozius.internship.project.entity.exception.IllegalDuplicateName;
import com.ozius.internship.project.entity.exception.IllegalPriceException;
import com.ozius.internship.project.entity.seller.Seller;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

import static com.ozius.internship.project.TestDataCreator.Categories.category1;
import static com.ozius.internship.project.TestDataCreator.Sellers.seller2;
import static com.ozius.internship.project.entity.Product.sellerProductNames;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
            Seller seller = entityFinder.getTheOne(Seller.class);
            if(sellerProductNames.containsKey(seller.getId())) {
                sellerProductNames.get(seller.getId()).remove(product.getName());
            }
            em.remove(product);
        });

        //----Assert
        assertThat(entityFinder.findAll(Product.class)).isEmpty();

    }

    @Test
    public void product_added_with_negative_price_throws_exception() {
        //----Act
        Exception exception = doTransaction(em -> {
            Category cat = em.merge(category1);
            Seller seller = em.merge(seller2);
            return assertThrows(IllegalPriceException.class, () -> {
                Product product = new Product("vinete", "descriereVinete", "/vinete", -2f, cat, seller);
                em.persist(product);
            });
        });

        //----Assert
        assertTrue(exception.getMessage().contains("Price cannot be negative!"));
    }

    @Test
    public void product_updated_with_negative_price_throws_exception() {
        //----Arrange
        doTransaction(em -> {
            Category cat = em.merge(category1);
            Seller seller = em.merge(seller2);
            Product product = new Product("vinete", "descriereVinete", "/vinete", 5F, cat, seller);
            em.persist(product);
        });

        //----Act
        Exception exception = doTransaction(em -> {
            EntityFinder entityFinder = new EntityFinder(em);
            Product pr = entityFinder.getTheOne(Product.class);
            return assertThrows(IllegalPriceException.class, () -> {
                pr.updateProduct("vinete_schimbate", "vinete mari", pr.getImageName(), -1F, pr.getCategory(), pr.getSeller());
            });
        });

        //----Assert
        assertTrue(exception.getMessage().contains("Price cannot be negative!"));
    }

    @Test
    public void product_added_with_already_existing_name_and_same_seller_throws_exception() {
        //----Arrange
        Product product = doTransaction(em -> {
            Category cat = em.merge(category1);
            Seller seller = em.merge(seller2);
            Product pr = new Product("vinete", "descriereVinete", "/vinete", 5F, cat, seller);
            em.persist(pr);
            return pr;
        });

        //----Act
        Exception exception = doTransaction(em -> {
            return assertThrows(IllegalDuplicateName.class, () -> {
                Product newProd = new Product("vinete", "descriereVinete", "/vinete", 5F, product.getCategory(), product.getSeller());
                em.persist(newProd);
            });
        });

        //----Assert
        assertTrue(exception.getMessage().contains("Seller already has a product with this name!"));
    }

    @Test
    public void product_updated_with_already_existing_name_and_same_seller_throws_exception() {
        //----Arrange
        Product product = doTransaction(em -> {
            Category cat = em.merge(category1);
            Seller seller = em.merge(seller2);
            Product product1 = new Product("lamaie", "lamaie", "/lamaie", 5F, cat, seller);
            Product product2 = new Product("kiwi", "kiwi", "/kiwi", 5F, cat, seller);
            em.persist(product1);
            em.persist(product2);

            return product1;
        });

        //----Act
        Exception exception = doTransaction(em -> {
            return assertThrows(IllegalDuplicateName.class, () -> {
                product.updateProduct("lamaie", product.getDescription(), product.getImageName(), product.getPrice(), product.getCategory(), product.getSeller());
            });
        });

        //----Assert
        assertTrue(exception.getMessage().contains("Seller already has a product with this name!"));
    }

}
