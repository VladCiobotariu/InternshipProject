package com.ozius.internship.project.entity;

import com.ozius.internship.project.TestDataCreatorErika;
import com.ozius.internship.project.entity.seller.Review;
import com.ozius.internship.project.entity.seller.Seller;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static com.ozius.internship.project.TestDataCreatorErika.Buyers.buyers1;
import static com.ozius.internship.project.TestDataCreatorErika.Buyers.buyers2;
import static com.ozius.internship.project.TestDataCreatorErika.Products.product1;
import static com.ozius.internship.project.TestDataCreatorErika.Sellers.seller1;
import static com.ozius.internship.project.TestDataCreatorErika.createReview;
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
            Seller seller = em.merge(seller1);
            Buyer buyer1 = em.merge(buyers1);
            Buyer buyer2 = em.merge(buyers2);
            Product product = em.merge(product1);

            seller.addReview(buyer1, "review 1", 5F, product);

            seller.addReview(buyer2, "review 2", 4F, product);

        });

        //----Assert
        Seller persistedSeller = entityFinder.findAll(Seller.class).get(0);

        assertThat(persistedSeller.getReviews()).hasSize(2);
        assertThat(persistedSeller.calculateRating()).isEqualTo(4.5);

    }

    @Test
    public void review_are_added_correctly() {
        //----Act
        doTransaction(em -> {
            Buyer buyer1 = em.merge(buyers1);
            Buyer buyer2 = em.merge(buyers2);
            Product product = em.merge(product1);

            // createReview adds the review to Seller directly (seller taken from product) -> in this case seller1
            createReview("review 1", 5F, buyer1, product, em);
            createReview("review 2", 4F, buyer2, product, em);

        });

        //----Assert
        Seller persistedSeller = entityFinder.findAll(Seller.class).get(0);

        Iterator<Review> iter = persistedSeller.getReviews().iterator();
        Review r1 = iter.next();
        Review r2 = iter.next();

        assertThat(r1.getDescription()).isEqualTo("review 1");
        assertThat(r1.getRating()).isEqualTo(5F);
        assertThat(r1.getProduct()).isEqualTo(product1);
        assertThat(r1.getBuyer()).isEqualTo(buyers1);

        assertThat(r2.getDescription()).isEqualTo("review 2");
        assertThat(r2.getRating()).isEqualTo(4F);
        assertThat(r2.getProduct()).isEqualTo(product1);
        assertThat(r2.getBuyer()).isEqualTo(buyers2);
    }

    @Test
    public void review_is_updated() {
        //----Arrange
        doTransaction(em -> {
            Seller seller = em.merge(seller1);
            Buyer buyer1 = em.merge(buyers1);
            Product product = em.merge(product1);

            seller.addReview(buyer1, "review 1", 5F, product);
        });

        //----Act
        doTransaction(em -> {
            EntityFinder entityFinder = new EntityFinder(em);
            Seller seller = entityFinder.findAll(Seller.class).get(0);
            Review reviewToUpdate = seller.getReviews().iterator().next();

            reviewToUpdate.updateReview("review updated", 4F);
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
            Seller seller = em.merge(seller1);
            Buyer buyer1 = em.merge(buyers1);
            Product product = em.merge(product1);

            seller.addReview(buyer1, "review 1", 5F, product);
        });

        //----Act
        doTransaction(em -> {
            EntityFinder entityFinder = new EntityFinder(em);
            Seller seller = entityFinder.findAll(Seller.class).get(0);
            Review reviewToRemove = seller.getReviews().iterator().next();
            seller.removeReview(reviewToRemove);
        });

        //----Assert
        Seller persistedSeller = entityFinder.findAll(Seller.class).get(0);
        assertThat(persistedSeller.getReviews()).isEmpty();
        assertThat(persistedSeller.calculateRating()).isEqualTo(0);
    }

}
