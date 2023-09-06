package com.ozius.internship.project;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile(SpringProfiles.TEST_DATA_SEED)
public class TestDataSeed {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @PostConstruct
    @Transactional
    public void createTestData(){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        TestDataCreator.createBuyerBaseData(em);
        TestDataCreator.createSellerBaseData(em);
        TestDataCreator.createCategoriesBaseData(em);
        TestDataCreator.createProductsBaseData(em);

        em.getTransaction().commit();
        em.close();
    }
}
