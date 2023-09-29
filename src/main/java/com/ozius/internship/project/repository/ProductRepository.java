package com.ozius.internship.project.repository;

import com.ozius.internship.project.entity.product.Product;
import com.ozius.internship.project.entity.seller.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT AVG(r.rating) FROM Seller s JOIN s.reviews r WHERE r.product = :product")
    Float calculateAverageRatingForProduct(@Param("product") Product product);

    @Query("SELECT r FROM Seller s JOIN s.reviews r WHERE r.product = :product")
    Set<Review> getReviewsForProduct(@Param("product") Product product);

}
