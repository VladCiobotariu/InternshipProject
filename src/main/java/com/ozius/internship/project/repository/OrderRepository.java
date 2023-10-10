package com.ozius.internship.project.repository;

import com.ozius.internship.project.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
