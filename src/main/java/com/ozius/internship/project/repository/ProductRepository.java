package com.ozius.internship.project.repository;

import com.ozius.internship.project.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);
}
