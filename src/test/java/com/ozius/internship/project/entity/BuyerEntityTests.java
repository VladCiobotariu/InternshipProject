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

import static com.ozius.internship.project.TestDataCreator.Buyers.buyer;
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
        //----Act
        BuyerInfo buyer = new BuyerInfo(new UserAccount("Cosmina", "Maria", "cosminamaria@gmail.com", "ozius1223423345", "/src/image2", "0735897635"));
        em.persist(buyer);

        em.flush();
        em.clear();

        //----Assert
        BuyerInfo persistedBuyer = buyerRepository.findById(buyer.getId()).orElseThrow();
        assertThat(persistedBuyer.getAccount().getFirstName()).isEqualTo("Cosmina");
        assertThat(persistedBuyer.getAccount().getLastName()).isEqualTo("Maria");
        assertThat(persistedBuyer.getAccount().getEmail()).isEqualTo("cosminamaria@gmail.com");
        assertThat(persistedBuyer.getAccount().getPasswordHash()).isEqualTo("ozius1223423345");
        assertThat(persistedBuyer.getAccount().getImageName()).isEqualTo("/src/image2");
        assertThat(persistedBuyer.getAccount().getTelephone()).isEqualTo("0735897635");
    }

    @Test
    void test_add_address(){
        //----Arrange
        TestDataCreator.createBuyerBaseData(em);

        //----Act
        Address address = new Address("Romania", "Timis", "Timisoara", "Strada Macilor 10", "Bloc 4, Scara F, ap 50", "300091");
        buyer.addAddress(address);

        em.flush();
        em.clear();

        //----Assert
        BuyerInfo persistedBuyer = buyerRepository.findById(buyer.getId()).orElseThrow();
        Address persistedAddress = persistedBuyer.getAddresses().stream().findFirst().get().getAddress();
        assertThat(persistedAddress.getCountry()).isEqualTo(address.getCountry());
        assertThat(persistedAddress.getState()).isEqualTo(address.getState());
        assertThat(persistedAddress.getCity()).isEqualTo(address.getCity());
        assertThat(persistedAddress.getAddressLine1()).isEqualTo(address.getAddressLine1());
        assertThat(persistedAddress.getAddressLine2()).isEqualTo(address.getAddressLine2());
        assertThat(persistedAddress.getZipCode()).isEqualTo(address.getZipCode());

    }

    @Test
    void test_buyer_updated(){

        //----Arrange
        TestDataCreator.createBuyerBaseData(em);

        //----Act
        buyer.updateEmail("cosminaa@gmail.com");

        em.flush();
        em.clear();

        //----Assert
        BuyerInfo persistedBuyer = buyerRepository.findById(buyer.getId()).orElseThrow();
        assertThat(persistedBuyer.getAccount().getEmail()).isEqualTo("cosminaa@gmail.com");

    }


    @Test
    void test_buyer_remove(){

        //----Arrange
        TestDataCreator.createBuyerBaseData(em);

        //----Act
        em.remove(buyer);

        em.flush();
        em.clear();

        //----Assert
        BuyerInfo persistedBuyer = em.find(BuyerInfo.class, buyer.getId());
        assertThat(persistedBuyer).isNull();

    }

    @Test
    void test_buyer_address_remove(){
        //----Arrange
        TestDataCreator.createBuyerBaseData(em);
        TestDataCreator.createAddressBaseData(em);

        BuyerAddress removeAddress = buyer.getAddresses().stream()
                .findFirst()
                .orElse(null);

        //----Act
        buyer.removeAddress(removeAddress);

        em.flush();
        em.clear();

        //----Assert
        BuyerInfo persistedBuyer = buyerRepository.findById(buyer.getId()).orElseThrow();
        assertThat(persistedBuyer.getAddresses()).isEmpty();
    }

}
