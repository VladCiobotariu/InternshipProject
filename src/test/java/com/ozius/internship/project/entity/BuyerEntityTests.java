package com.ozius.internship.project.entity;

import com.ozius.internship.project.TestDataCreator;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.util.List;

import static com.ozius.internship.project.TestDataCreator.Buyers.buyer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class BuyerEntityTests extends EntityBaseTest{

    private JpaRepository<Buyer, Long> buyerRepository;

    @Override
    public void createTestData(EntityManager em) {
        this.buyerRepository = new SimpleJpaRepository<>(Buyer.class, emb);
    }

    @Test
    void test_add_buyer(){
        //----Act
        doTransaction(em -> {
            Buyer buyer = new Buyer(new UserAccount("Cosmina", "Maria", "cosminamaria@gmail.com", "ozius1223423345", "/src/image2", "0735897635"));
            em.persist(buyer);
        });

        //----Assert
        Buyer persistedBuyer = buyerRepository.findAll().get(0);
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
        doTransaction(em -> {
            TestDataCreator.createBuyerBaseData(em);
        });

        //----Act
        doTransaction(em -> {
            Address address = new Address("Romania", "Timis", "Timisoara", "Strada Macilor 10", "Bloc 4, Scara F, ap 50", "300091");
            Buyer buyer = em.merge(TestDataCreator.Buyers.buyer);
            buyer.addAddress(address);
        });

        //----Assert
        Buyer persistedBuyer = buyerRepository.findAll().get(0);
        Address persistedAddress = persistedBuyer.getAddresses().stream().findFirst().get().getAddress();
        assertThat(persistedAddress.getCountry()).isEqualTo("Romania");
        assertThat(persistedAddress.getState()).isEqualTo("Timis");
        assertThat(persistedAddress.getCity()).isEqualTo("Timisoara");
        assertThat(persistedAddress.getAddressLine1()).isEqualTo("Strada Macilor 10");
        assertThat(persistedAddress.getAddressLine2()).isEqualTo("Bloc 4, Scara F, ap 50");
        assertThat(persistedAddress.getZipCode()).isEqualTo("300091");

    }

    @Test
    void test_buyer_updated(){

        //----Arrange
        doTransaction(em -> {
            TestDataCreator.createBuyerBaseData(em);
        });

        //----Act
        doTransaction(em -> {
            Buyer buyer = em.merge(TestDataCreator.Buyers.buyer);
            buyer.updateEmail("cosminaa@gmail.com");
        });

        //----Assert
        Buyer persistedBuyer = buyerRepository.findAll().get(0);
        assertThat(persistedBuyer.getAccount().getEmail()).isEqualTo("cosminaa@gmail.com");

    }


    @Test
    void test_buyer_remove(){

        //----Arrange
        doTransaction(em -> {
            TestDataCreator.createBuyerBaseData(em);
            TestDataCreator.createAddressBaseData(em);
        });

        //----Act
        doTransaction(em -> {
            Buyer buyer = em.merge(TestDataCreator.Buyers.buyer);
            em.remove(buyer);
        });

        //----Assert
        List<Buyer> persistedBuyer = buyerRepository.findAll();
        assertThat(persistedBuyer).isEmpty();
        List<BuyerAddress> buyerAddress = new SimpleJpaRepository<>(BuyerAddress.class, emb).findAll();
        assertThat(buyerAddress).isEmpty();
    }

    @Test
    void test_buyer_address_remove(){
        //----Arrange
        doTransaction(em -> {
            TestDataCreator.createBuyerBaseData(em);
            TestDataCreator.createAddressBaseData(em);
        });

        //----Act
        doTransaction(em -> {
            Buyer buyer = em.merge(TestDataCreator.Buyers.buyer);

            BuyerAddress removeAddress = buyer.getAddresses().stream()
                    .findFirst()
                    .orElse(null);

            buyer.removeAddress(removeAddress);

            System.out.println("Buyer addreses:");
            buyer.getAddresses().stream().forEach(System.out::println);

            System.out.println("Repository addreses:");
            new SimpleJpaRepository<>(BuyerAddress.class, em).findAll().forEach(System.out::println);
        });

        emb.clear();
        //----Assert
        Buyer persistedBuyer = buyerRepository.findAll().get(0);
        assertThat(persistedBuyer.getAddresses()).isEmpty();
    }

}
