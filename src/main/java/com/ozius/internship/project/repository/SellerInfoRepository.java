package com.ozius.internship.project.repository;

import com.ozius.internship.project.entity.seller.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerInfoRepository extends JpaRepository<Seller, Long> {
}
