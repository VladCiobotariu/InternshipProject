package com.ozius.internship.project.entity;

import com.ozius.internship.project.infra.JpaCallbackVoid;
import com.ozius.internship.project.infra.JpaHelper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class EntityBaseTest {

    @PersistenceContext
    protected EntityManager emb;

    @PersistenceUnit
    protected EntityManagerFactory emf;

    protected EntityFinder entityFinder;

    @BeforeEach
    void setUp() {
        emb = emf.createEntityManager();

        entityFinder = new EntityFinder(emb);

        doTransaction(em -> {
            createTestData(em);
        });
        // emb - folosit pentru cazurile in care avem nevoie de un Entity Manager deschis (query, pt lazy conn, pt persistare)
    }

    @AfterEach
    void tearUp() {
        emb.close();
    }

    public void createTestData(EntityManager em) {

    }
    public void doTransaction(JpaCallbackVoid callback) {
        new JpaHelper(emf).doTransaction(em -> {
            callback.execute(em);
        });
    }

    public void doManaged(JpaCallbackVoid callback) {
        new JpaHelper(emf).doManaged(em -> {
            callback.execute(em);
        });
    }

}
