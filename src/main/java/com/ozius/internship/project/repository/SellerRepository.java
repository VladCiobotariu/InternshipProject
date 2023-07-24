package com.ozius.internship.project.repository;

import com.ozius.internship.project.entity.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<SellerInfo, Long> {
}
