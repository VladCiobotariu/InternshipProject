package com.ozius.internship.project.entity.seller;

import com.ozius.internship.project.TestDataCreator;
import com.ozius.internship.project.entity.buyer.Buyer;
import com.ozius.internship.project.entity.EntityBaseTest;
import com.ozius.internship.project.entity.Product;
import com.ozius.internship.project.entity.exeption.IllegalItemExeption;
import com.ozius.internship.project.entity.exeption.IllegalRatingException;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

import static com.ozius.internship.project.TestDataCreator.Products.product1;
import static com.ozius.internship.project.TestDataCreator.Buyers.buyer1;
import static com.ozius.internship.project.TestDataCreator.Sellers.seller1;
import static com.ozius.internship.project.TestDataCreator.Sellers.seller2;
import static com.ozius.internship.project.TestDataCreator.createReview;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SellerReviewEntityTests extends EntityBaseTest {
    @Override
    public void createTestData(EntityManager em) {
        TestDataCreator.createBaseDataForReview(em);
    }

    @Test
    public void reviews_are_added() {
        //----Act
        Seller savedSeller = doTransaction(em -> {
            Buyer buyer1 = em.merge(TestDataCreator.Buyers.buyer1);
            Buyer buyer2 = em.merge(TestDataCreator.Buyers.buyer2);
            Product product = em.merge(product1);
            Seller seller = product.getSeller();

            seller.addReview(buyer1, "review 1", 5F, product);
            seller.addReview(buyer2, "review 2", 4F, product);

            return seller;

        });

        //----Assert
        assertThat(savedSeller.getReviews()).hasSize(2);
        assertThat(savedSeller.calculateRating()).isEqualTo(4.5);

    }

    @Test
    public void review_is_added_correctly() {
        //----Act
        Seller mergedSeller = doTransaction(em -> {
            Buyer buyer1 = em.merge(TestDataCreator.Buyers.buyer1);
            Product product = em.merge(product1);
            Seller seller = em.merge(product.getSeller());

            // in create review, Review is for sure added to the correct seller
            createReview(buyer1, "review 1", 5F, product);

            return seller;
        });

        //----Assert

        Review review = mergedSeller.getReviews().stream().findFirst().orElseThrow();

        assertThat(review.getDescription()).isEqualTo("review 1");
        assertThat(review.getRating()).isEqualTo(5F);
        assertThat(review.getProduct()).isEqualTo(product1);
        assertThat(review.getBuyer()).isEqualTo(buyer1);

    }

    @Test
    public void review_is_updated() {
        //----Arrange
        Seller savedSeller = doTransaction(em -> {
            Buyer buyer1 = em.merge(TestDataCreator.Buyers.buyer1);
            Product product = em.merge(product1);
            Seller seller = product.getSeller();
            seller.addReview(buyer1, "review 1", 5F, product);

            return seller;
        });

        //----Act
        doTransaction(em -> {
            Review reviewToUpdate = savedSeller.getReviews().stream().findFirst().orElseThrow();
            reviewToUpdate.updateReview("review updated", 4F);
        });

        //----Assert
        assertThat(savedSeller.calculateRating()).isEqualTo(4F);

        Review review = savedSeller.getReviews().stream().findFirst().orElseThrow();
        assertThat(review.getDescription()).isEqualTo("review updated");
        assertThat(review.getRating()).isEqualTo(4);
        assertThat(review.getBuyer()).isEqualTo(buyer1);
        assertThat(review.getProduct()).isEqualTo(product1);
    }

    @Test
    public void review_added_wrong_product_throws_exception(){
        //----Act
        Exception exception = doTransaction(em -> {
            Buyer buyer1 = em.merge(TestDataCreator.Buyers.buyer1);
            Seller seller = em.merge(seller2);
            Product product = em.merge(product1); // product1 has seller1

            return assertThrows(IllegalItemExeption.class, () -> seller.addReview(buyer1, "review 1", 5F, product));
        });

        //----Assert
        assertTrue(exception.getMessage().contains("can't add review, product must correspond to this seller"));
    }

    @Test
    public void review_added_wrong_rating_throws_exception() {
        // ----Act
        Exception exception = doTransaction(em -> {
            Buyer buyer1 = em.merge(TestDataCreator.Buyers.buyer1);
            Seller seller = em.merge(seller1);
            Product product = em.merge(product1); // product1 has seller1

            return assertThrows(IllegalRatingException.class, () -> {
                seller.addReview(buyer1, "review 1", 8f, product);
            });
        });

        // ----Assert
        assertTrue(exception.getMessage().contains("Rating must be between 0 and 5!"));
    }

    @Test
    public void review_updated_wrong_from_rating_throws_exception() {
        //----Arrange
        Seller savedSeller = doTransaction(em -> {
            Buyer buyer1 = em.merge(TestDataCreator.Buyers.buyer1);
            Product product = em.merge(product1);
            Seller seller = product.getSeller();
            seller.addReview(buyer1, "review 1", 5F, product);

            return seller;
        });

        //----Act
        Exception exception = doTransaction(em -> {
            Review reviewToUpdate = savedSeller.getReviews().stream().findFirst().orElseThrow();
            return assertThrows(IllegalRatingException.class, () -> {
                reviewToUpdate.updateReview("review updated", -2F);
            });
        });

        //----Assert
        assertTrue(exception.getMessage().contains("Rating must be between 0 and 5!"));
    }

}
