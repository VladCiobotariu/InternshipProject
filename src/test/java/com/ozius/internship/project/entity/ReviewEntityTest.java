package com.ozius.internship.project.entity;

import com.ozius.internship.project.TestDataCreator;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import static org.assertj.core.api.Assertions.assertThat;

public class ReviewEntityTest extends EntityBaseTest{
    private JpaRepository<Seller, Long> sellerRepository;
    @Override
    public void createTestData(EntityManager em) {
        this.sellerRepository = new SimpleJpaRepository<>(Seller.class, emb);
        TestDataCreator.createBaseDataForReview(em);
    }

    @Test
    public void reviews_are_added() {
        //----Act
        doTransaction(em -> {
            Seller seller = em.merge(TestDataCreator.Sellers.seller1);
            Buyer buyer1 = em.merge(TestDataCreator.Buyers.buyers1);
            Buyer buyer2 = em.merge(TestDataCreator.Buyers.buyers2);
            Product product = em.merge(TestDataCreator.Products.product1);

            Review review1 = new Review("review 1", 5F, buyer1, product);
            seller.addReview(review1.getBuyerInfo(), review1.getDescription(), review1.getRating(), review1.getProduct());

            Review review2 = new Review("review 2", 4F, buyer2, product);
            seller.addReview(review2.getBuyerInfo(), review2.getDescription(), review2.getRating(), review2.getProduct());

        });

        //----Assert
        Seller persistedSeller = sellerRepository.findAll().get(0);

        assertThat(persistedSeller.getReviews()).hasSize(2);
        assertThat(persistedSeller.calculateRating()).isEqualTo(4.5);

    }

    @Test
    public void review_is_updated() {
        //----Arrange
        doTransaction(em -> {
            Seller seller = em.merge(TestDataCreator.Sellers.seller1);
            Buyer buyer1 = em.merge(TestDataCreator.Buyers.buyers1);
            Product product = em.merge(TestDataCreator.Products.product1);

            Review review = new Review("review", 5F, buyer1, product);
            seller.addReview(review.getBuyerInfo(), review.getDescription(), review.getRating(), review.getProduct());
        });

        //----Act
        doTransaction(em -> {
            Seller seller = sellerRepository.findAll().get(0);
            Seller managedSeller = em.merge(seller);
            Review reviewToUpdate = managedSeller.getReviews().iterator().next();

            reviewToUpdate.setDescription("review updated");
            reviewToUpdate.setRating(4F);
        });

        //----Assert
        Seller seller = sellerRepository.findAll().get(0);
        assertThat(seller.calculateRating()).isEqualTo(4F);

        Review review = seller.getReviews().iterator().next();
        assertThat(review.getDescription()).isEqualTo("review updated");
        assertThat(review.getRating()).isEqualTo(4);

    }

    @Test
    public void review_is_deleted() {
        //----Arrange
        doTransaction(em -> {
            Seller seller = em.merge(TestDataCreator.Sellers.seller1);
            Buyer buyer1 = em.merge(TestDataCreator.Buyers.buyers1);
            Product product = em.merge(TestDataCreator.Products.product1);

            Review review = new Review("review", 5F, buyer1, product);
            seller.addReview(review.getBuyerInfo(), review.getDescription(), review.getRating(), review.getProduct());
        });

        //----Act
        doTransaction(em -> {
            Seller seller = sellerRepository.findAll().get(0);
            Seller sellerForRemove = em.merge(seller);
            Review reviewToRemove = sellerForRemove.getReviews().iterator().next();
            seller.removeReview(reviewToRemove);
        });

        //----Assert
        Seller persistedSeller = sellerRepository.findAll().get(0);
        assertThat(persistedSeller.getReviews()).isEmpty();
        assertThat(persistedSeller.calculateRating()).isEqualTo(0);
    }

}
