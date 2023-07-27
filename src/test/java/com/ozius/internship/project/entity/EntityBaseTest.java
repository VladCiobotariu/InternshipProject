package com.ozius.internship.project.entity;

import com.ozius.internship.project.infra.JpaCallback;
import com.ozius.internship.project.infra.JpaCallbackVoid;
import com.ozius.internship.project.infra.JpaHelper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class EntityBaseTest {

    @PersistenceUnit
    private EntityManagerFactory emf;

    protected EntityManager emb;

    @BeforeEach
    void setUp() {
        emb = emf.createEntityManager();

        doTransaction(em -> {
            createTestData(em);
        });
    }

    @AfterEach
    void tearDown() {

        emb.close();
    }

    public void createTestData(EntityManager em){

    }

    public void doTransaction(JpaCallbackVoid callback){
        new JpaHelper(emf).doTransaction(em -> {
            callback.execute(em);
        });
    }

    public void doManaged(JpaCallbackVoid callback){
        new JpaHelper(emf).doManaged(em -> {
            callback.execute(em);
        });
    }

    public <T> T doTransaction(JpaCallback<T> callback){
        return new JpaHelper(emf).doTransaction(em -> {
            return callback.execute(em);
        });
    }
}
