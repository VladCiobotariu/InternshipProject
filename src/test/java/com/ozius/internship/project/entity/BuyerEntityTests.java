package com.ozius.internship.project.entity;

import com.ozius.internship.project.TestDataCreator;
import com.ozius.internship.project.repository.BuyerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@DataJpaTest
public class BuyerEntityTests {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private BuyerRepository buyerRepository;

    @BeforeEach
    void setUp() {

    }

    @Test
    void test_add_buyer(){

        BuyerInfo buyer = new BuyerInfo(new UserAccount("Cosmina", "Maria", "cosminamaria@gmail.com", "ozius1223423345", "/src/image2", "0735897635"));
        em.persist(buyer);

        assertThat(buyer).isEqualTo(buyerRepository.findById(1l).get());

        Address address = new Address("Romania", "Timis", "Timisoara", "Strada Macilor 10", "Bloc 4, Scara F, ap 50", "300091");
        buyer.addAddress(address);

        assertThat(buyer.getAddresses().stream().findFirst().get().getAddress()).isEqualTo(address);
    }

    @Test
    void test_buyer_updated(){

        TestDataCreator.createBuyerBaseData(em);
        BuyerInfo buyer = buyerRepository.findById(1l).get();

        buyer.updateEmail("cosminaa@gmail.com");

        assertThat(buyerRepository.findById(1l).get().getAccount().getEmail()).isEqualTo("cosminaa@gmail.com");

    }


    @Test
    void test_buyer_remove(){

        TestDataCreator.createBuyerBaseData(em);
        TestDataCreator.createAddressBaseData(em);

        BuyerInfo buyer = buyerRepository.findById(1l).get();

        BuyerAddress removeAddress = buyer.getAddresses().stream()
                        .filter(address -> address.getId() == 0L)
                        .findFirst()
                        .orElse(null);

        buyer.removeAddress(removeAddress);
//        buyer.getAddresses().forEach(System.out::println);

        assertThat(buyer.getAddresses()).isEmpty();

        BuyerInfo removeBuyer = em.find(BuyerInfo.class, 1l);
        em.remove(removeBuyer);
        try {
            buyerRepository.findById(1l).get();
            fail("expected to fail");
        }catch (NoSuchElementException ex){
            //nothing to do expected to fail
        }

    }

}
