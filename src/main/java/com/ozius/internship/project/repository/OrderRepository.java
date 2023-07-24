package com.ozius.internship.project.repository;

import com.ozius.internship.project.entity.Order;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;

@Registered
public interface OrderRepository extends JpaRepository<Order, Long> {
}
