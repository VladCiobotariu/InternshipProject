package com.ozius.internship.project.entity;

import com.ozius.internship.project.TestDataCreator;
import com.ozius.internship.project.entity.seller.Review;
import com.ozius.internship.project.entity.seller.Seller;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static com.ozius.internship.project.TestDataCreator.Products.product1;
import static com.ozius.internship.project.TestDataCreator.Products.product3;
import static com.ozius.internship.project.TestDataCreator.Buyers.buyer1;
import static com.ozius.internship.project.TestDataCreator.Buyers.buyer2;
import static com.ozius.internship.project.TestDataCreator.Sellers.seller2;
import static com.ozius.internship.project.TestDataCreator.createReview;
import static org.assertj.core.api.Assertions.assertThat;

public class ReviewEntityTest extends EntityBaseTest{
    @Override
    public void createTestData(EntityManager em) {
        TestDataCreator.createBaseDataForReview(em);
    }

    @Test
    public void reviews_are_added() {
        //----Act
        doTransaction(em -> {
            Seller seller = em.merge(seller2);
            Buyer buyer1 = em.merge(TestDataCreator.Buyers.buyer1);
            Buyer buyer2 = em.merge(TestDataCreator.Buyers.buyer2);
            Product product = em.merge(product3);

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
            Buyer buyer1 = em.merge(TestDataCreator.Buyers.buyer1);
            Buyer buyer2 = em.merge(TestDataCreator.Buyers.buyer2);
            Product product = em.merge(product1);

            createReview(buyer1, "review 1", 5F, product);
            createReview(buyer2,"review 2", 4F, product);

        });

        Seller persistedSeller = entityFinder.findAll(Seller.class).get(0);

        //----Assert
        Iterator<Review> iter = persistedSeller.getReviews().iterator();
        Review r1 = iter.next();
        Review r2 = iter.next();

        assertThat(r1.getDescription()).isEqualTo("review 1");
        assertThat(r1.getRating()).isEqualTo(5F);
        assertThat(r1.getProduct()).isEqualTo(product1);
        assertThat(r1.getBuyer()).isEqualTo(buyer1);

        assertThat(r2.getDescription()).isEqualTo("review 2");
        assertThat(r2.getRating()).isEqualTo(4F);
        assertThat(r2.getProduct()).isEqualTo(product1);
        assertThat(r2.getBuyer()).isEqualTo(buyer2);
    }

    @Test
    public void review_is_updated() {
        //----Arrange
        doTransaction(em -> {
            Seller seller = em.merge(seller2);
            Buyer buyer1 = em.merge(TestDataCreator.Buyers.buyer1);
            Product product = em.merge(product3);

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
            Seller seller = em.merge(seller2);
            Buyer buyer1 = em.merge(TestDataCreator.Buyers.buyer1);
            Product product = em.merge(product3);

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
