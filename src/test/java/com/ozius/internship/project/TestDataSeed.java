package com.ozius.internship.project;

import com.ozius.internship.project.entity.Category;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
public class TestDataSeed {

    @PersistenceUnit
    private EntityManagerFactory emf;


    @PostConstruct
    @Transactional
    public void createTestData() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        TestDataCreator.createCategoriesBaseData(em);

        em.getTransaction().commit();
        em.close();
    }
}
