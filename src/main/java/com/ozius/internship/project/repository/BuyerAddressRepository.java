package com.ozius.internship.project.repository;

import com.ozius.internship.project.entity.buyer.BuyerAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyerAddressRepository extends JpaRepository<BuyerAddress, Long> {
}
