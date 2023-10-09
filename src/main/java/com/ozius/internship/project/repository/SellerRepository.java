package com.ozius.internship.project.repository;

import com.ozius.internship.project.entity.seller.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
    Optional<Seller> findSellerByAccount_Email(String email);
}
