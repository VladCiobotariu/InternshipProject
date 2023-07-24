package com.ozius.internship.project.entity;

import com.ozius.internship.project.repository.BuyerRepository;
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

    @BeforeEach
    void setUp() {

    }

    @Test
    void test_add_order(){

    }

    @Test
    void test_update_order(){

    }

    @Test
    void test_remove_order(){

    }
}
