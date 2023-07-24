package com.ozius.internship.project.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class SellerEntityTests {

    @PersistenceContext
    private EntityManager em;

    @BeforeEach
    void setUp() {

    }

    @Test
    void test_add_seller(){

    }

    @Test
    void test_update_seller(){

    }

    @Test
    void test_remove_seller(){

    }

}
