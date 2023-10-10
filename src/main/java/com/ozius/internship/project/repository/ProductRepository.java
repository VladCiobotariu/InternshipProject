package com.ozius.internship.project.repository;

import com.ozius.internship.project.domain.product.Product;
import com.ozius.internship.project.domain.seller.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT r FROM Seller s JOIN s.reviews r WHERE r.product.id = :id")
    List<Review> getReviewsForProduct(@Param("id") long id);

}
