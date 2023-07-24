package com.ozius.internship.project.entity;

import com.ozius.internship.project.TestDataCreator;
import com.ozius.internship.project.entity.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class OrderEntityTests {

    @PersistenceContext
    private EntityManager em;

    @Autowired
   // private OrdeRepo

    @BeforeEach
    public void setupTestData() {
        TestDataCreator.createBaseData(em);
    }

    @Test
    public void test_order_is_created() {
        Order order = new Order();

    }

    @Test
    public void test_order_is_updated() {

    }

    @Test
    public void test_order_is_remove() {

    }

}
