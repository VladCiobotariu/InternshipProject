package com.ozius.internship.project.repository;

import com.ozius.internship.project.entity.BuyerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyerInfoRepository extends JpaRepository<BuyerInfo, Long> {
}
