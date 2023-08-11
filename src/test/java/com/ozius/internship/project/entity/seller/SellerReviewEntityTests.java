package com.ozius.internship.project.entity.seller;

import com.ozius.internship.project.TestDataCreator;
import com.ozius.internship.project.entity.Buyer;
import com.ozius.internship.project.entity.EntityBaseTest;
import com.ozius.internship.project.entity.EntityFinder;
import com.ozius.internship.project.entity.Product;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

import static com.ozius.internship.project.TestDataCreator.Products.product1;
import static com.ozius.internship.project.TestDataCreator.Products.product3;
import static com.ozius.internship.project.TestDataCreator.Buyers.buyer1;
import static com.ozius.internship.project.TestDataCreator.Sellers.seller2;
import static com.ozius.internship.project.TestDataCreator.createReview;
import static org.assertj.core.api.Assertions.assertThat;

public class SellerReviewEntityTests extends EntityBaseTest {
    @Override
    public void createTestData(EntityManager em) {
        TestDataCreator.createBaseDataForReview(em);
    }

    @Test
    public void reviews_are_added() {
        //----Act
        Seller changedSeller = doTransaction(em -> {
            Seller seller = em.merge(seller2);
            Buyer buyer1 = em.merge(TestDataCreator.Buyers.buyer1);
            Buyer buyer2 = em.merge(TestDataCreator.Buyers.buyer2);
            Product product = em.merge(product3);

            seller.addReview(buyer1, "review 1", 5F, product);
            seller.addReview(buyer2, "review 2", 4F, product);

            return seller;

        });

        //----Assert
        assertThat(changedSeller.getReviews()).hasSize(2);
        assertThat(changedSeller.calculateRating()).isEqualTo(4.5);

    }

    @Test
    public void review_is_added_correctly() {
        //----Act
        doTransaction(em -> {
            Buyer buyer1 = em.merge(TestDataCreator.Buyers.buyer1);
            Product product = em.merge(product1);

            createReview(buyer1, "review 1", 5F, product);

        });

        //----Assert
        Seller persistedSeller = entityFinder.findAll(Seller.class).get(0);
        Review review = persistedSeller.getReviews().stream().findFirst().orElseThrow();

        assertThat(review.getDescription()).isEqualTo("review 1");
        assertThat(review.getRating()).isEqualTo(5F);
        assertThat(review.getProduct()).isEqualTo(product1);
        assertThat(review.getBuyer()).isEqualTo(buyer1);

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
            Review reviewToUpdate = seller.getReviews().stream().findFirst().orElseThrow();

            reviewToUpdate.updateReview("review updated", 4F);
        });

        //----Assert
        Seller seller = entityFinder.findAll(Seller.class).get(0);
        assertThat(seller.calculateRating()).isEqualTo(4F);

        Review review = seller.getReviews().stream().findFirst().orElseThrow();
        assertThat(review.getDescription()).isEqualTo("review updated");
        assertThat(review.getRating()).isEqualTo(4);

    }

}
