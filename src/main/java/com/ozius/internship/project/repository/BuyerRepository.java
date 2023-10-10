package com.ozius.internship.project.repository;

import com.ozius.internship.project.domain.buyer.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {
    Buyer findBuyerByAccount_Id(long id);
    Optional<Buyer> findBuyerByAccount_Email(String email);
}
