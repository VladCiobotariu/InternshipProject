package com.ozius.internship.project.repository;

import com.ozius.internship.project.entity.buyer.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {
    Buyer findBuyerByAccount_Id(long id);
}
