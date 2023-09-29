package com.ozius.internship.project.repository;

import com.ozius.internship.project.entity.product.Product;
import com.ozius.internship.project.entity.seller.Review;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // todo - ask if tuple is ok to use here
    @Query("SELECT AVG(r.rating) AS productRating, COUNT(r) AS numberReviews FROM Seller s JOIN s.reviews r WHERE r.product.id = :id")
    Tuple calculateReviewInfoForProduct(@Param("id") long id);

    @Query("SELECT r FROM Seller s JOIN s.reviews r WHERE r.product.id = :id")
    List<Review> getReviewsForProduct(@Param("id") long id);

}
