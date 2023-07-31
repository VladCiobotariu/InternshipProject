package com.ozius.internship.project.entity;

import com.ozius.internship.project.TestDataCreatorErika;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ReviewEntityTest extends EntityBaseTest{
    @Override
    public void createTestData(EntityManager em) {
        TestDataCreatorErika.createBaseDataForReview(em);
    }

    @Test
    public void reviews_are_added() {
        //----Act
        doTransaction(em -> {
            Seller seller = em.merge(TestDataCreatorErika.Sellers.seller1);
            Buyer buyer1 = em.merge(TestDataCreatorErika.Buyers.buyers1);
            Buyer buyer2 = em.merge(TestDataCreatorErika.Buyers.buyers2);
            Product product = em.merge(TestDataCreatorErika.Products.product1);

            Review review1 = new Review("review 1", 5F, buyer1, product);
            seller.addReview(review1.getBuyer(), review1.getDescription(), review1.getRating(), review1.getProduct());

            Review review2 = new Review("review 2", 4F, buyer2, product);
            seller.addReview(review2.getBuyer(), review2.getDescription(), review2.getRating(), review2.getProduct());

        });

        //----Assert
        Seller persistedSeller = entityFinder.findAll(Seller.class).get(0);

        assertThat(persistedSeller.getReviews()).hasSize(2);
        assertThat(persistedSeller.calculateRating()).isEqualTo(4.5);

    }

    @Test
    public void review_is_updated() {
        //----Arrange
        doTransaction(em -> {
            Seller seller = em.merge(TestDataCreatorErika.Sellers.seller1);
            Buyer buyer1 = em.merge(TestDataCreatorErika.Buyers.buyers1);
            Product product = em.merge(TestDataCreatorErika.Products.product1);

            Review review = new Review("review", 5F, buyer1, product);
            seller.addReview(review.getBuyer(), review.getDescription(), review.getRating(), review.getProduct());
        });

        //----Act
        doTransaction(em -> {
            EntityFinder entityFinder = new EntityFinder(em);
            Seller seller = entityFinder.findAll(Seller.class).get(0);
            Seller managedSeller = em.merge(seller);
            Review reviewToUpdate = managedSeller.getReviews().iterator().next();

            reviewToUpdate.updateDescription("review updated");
            reviewToUpdate.updateRating(4F);
        });

        //----Assert
        Seller seller = entityFinder.findAll(Seller.class).get(0);
        assertThat(seller.calculateRating()).isEqualTo(4F);

        Review review = seller.getReviews().iterator().next();
        assertThat(review.getDescription()).isEqualTo("review updated");
        assertThat(review.getRating()).isEqualTo(4);

    }

    @Test
    public void review_is_deleted() {
        //----Arrange
        doTransaction(em -> {
            Seller seller = em.merge(TestDataCreatorErika.Sellers.seller1);
            Buyer buyer1 = em.merge(TestDataCreatorErika.Buyers.buyers1);
            Product product = em.merge(TestDataCreatorErika.Products.product1);

            Review review = new Review("review", 5F, buyer1, product);
            seller.addReview(review.getBuyer(), review.getDescription(), review.getRating(), review.getProduct());
        });

        //----Act
        doTransaction(em -> {
            EntityFinder entityFinder = new EntityFinder(em);
            Seller seller = entityFinder.findAll(Seller.class).get(0);
            Seller sellerForRemove = em.merge(seller);
            Review reviewToRemove = sellerForRemove.getReviews().iterator().next();
            seller.removeReview(reviewToRemove);
        });

        //----Assert
        Seller persistedSeller = entityFinder.findAll(Seller.class).get(0);
        assertThat(persistedSeller.getReviews()).isEmpty();
        assertThat(persistedSeller.calculateRating()).isEqualTo(0);
    }

}
