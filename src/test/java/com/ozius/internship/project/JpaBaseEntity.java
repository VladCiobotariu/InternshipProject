package com.ozius.internship.project;

import com.ozius.internship.project.infra.JpaCallback;
import com.ozius.internship.project.infra.JpaCallbackVoid;
import com.ozius.internship.project.infra.JpaHelper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = ProjectApplicationTest.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles({SpringProfiles.DEV, SpringProfiles.EMBEDDED_DB_H2})
public class JpaBaseEntity {

    @PersistenceUnit
    private EntityManagerFactory emf;

    protected EntityManager emb;

    protected EntityFinder entityFinder;

    @BeforeEach
    void setUp() {
        emb = emf.createEntityManager();

        entityFinder = new EntityFinder(emb);

        doTransaction(this::createTestData);
    }

    @AfterEach
    void tearDown() {
        emb.close();
    }

    public void createTestData(EntityManager em){

    }

    public void doTransaction(JpaCallbackVoid callback){
        new JpaHelper(emf).doTransaction(callback);
    }

    public void doManaged(JpaCallbackVoid callback){
        new JpaHelper(emf).doManaged(callback);
    }

    public <T> T doTransaction(JpaCallback<T> callback){
        return new JpaHelper(emf).doTransaction(callback);
    }
}
