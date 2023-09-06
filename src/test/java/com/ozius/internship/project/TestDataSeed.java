package com.ozius.internship.project;

import com.ozius.internship.project.infra.JpaHelper;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile(SpringProfiles.TEST_DATA_SEED)
public class TestDataSeed {

    @PersistenceUnit
    private EntityManagerFactory emf;

    private final PasswordEncoder passwordEncoder;

    public TestDataSeed(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void createTestData(){

        new JpaHelper(emf).doTransaction(em -> {

            TestDataCreator.createBuyerBaseData(em, passwordEncoder);
            TestDataCreator.createSellerBaseData(em);
            TestDataCreator.createCategoriesBaseData(em);
            TestDataCreator.createProductsBaseData(em);

        });
    }

}
