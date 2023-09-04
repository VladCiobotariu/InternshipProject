package com.ozius.internship.project.repository;

import com.ozius.internship.project.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByName(String name);

    @Query("SELECT p FROM Product p JOIN p.category c ON c.name = :categoryName")
    List<Product> findByCategoryName(String categoryName);
}
