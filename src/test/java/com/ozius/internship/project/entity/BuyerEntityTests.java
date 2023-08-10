package com.ozius.internship.project.entity;

import com.ozius.internship.project.TestDataCreator;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
        Buyer buyer = doTransaction(em -> {
            UserAccount account = new UserAccount("Cosmina", "Maria", "cosminamaria@gmail.com", "ozius1223423345", "/src/image2", "0735897635");
            return TestDataCreator.createBuyer(em, account);
        });

        //----Act
        Address addedAddress = doTransaction(em -> {
            Address address = new Address("Romania", "Timis", "Timisoara", "Strada Macilor 10", "Bloc 4, Scara F, ap 50", "300091");
            Buyer mergedBuyer = em.merge(buyer);
            mergedBuyer.addAddress(address);

            return address;
        });

        //----Assert
        Buyer persistedBuyer = entityFinder.getTheOne(Buyer.class);

        Address persistedAddress = persistedBuyer.getAddresses().stream().findFirst().orElseThrow().getAddress();
        assertThat(persistedAddress).isEqualTo(addedAddress);

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
        Buyer buyer = doTransaction(em -> {
            UserAccount account = new UserAccount("Cosmina", "Maria", "cosminamaria@gmail.com", "ozius1223423345", "/src/image2", "0735897635");
            return TestDataCreator.createBuyer(em, account);
        });

        //----Act
        Buyer addedBuyer= doTransaction(em -> {
            Buyer mergedBuyer = em.merge(buyer);
            mergedBuyer.updateEmail("cosminaa@gmail.com");

            return mergedBuyer;
        });

        //----Assert
        Buyer persistedBuyer = entityFinder.getTheOne(Buyer.class);
        assertThat(persistedBuyer.getAccount().getEmail()).isEqualTo("cosminaa@gmail.com");
        assertThat(persistedBuyer).isEqualTo(addedBuyer);
        assertThat(persistedBuyer).isNotSameAs(buyer);
    }


    @Test
    void test_buyer_remove(){

        //----Arrange
        Buyer buyer = doTransaction(em -> {
            UserAccount account = new UserAccount("Cosmina", "Maria", "cosminamaria@gmail.com", "ozius1223423345", "/src/image2", "0735897635");
            Address address = new Address("Romania", "Timis", "Timisoara", "Strada Macilor 10", "Bloc 4, Scara F, ap 50", "300091");

            Buyer buyerToAdd = TestDataCreator.createBuyer(em, account);
            TestDataCreator.addAddressBuyer(em, buyerToAdd, address);

            return buyerToAdd;
        });

        //----Act
        doTransaction(em -> {
            Buyer mergedBuyer = em.merge(buyer);
            em.remove(mergedBuyer);
        });

        //----Assert
        List<BuyerAddress> buyerAddress = new SimpleJpaRepository<>(BuyerAddress.class, emb).findAll();

        assertThat(buyerRepository.findAll().contains(buyer)).isFalse();
        assertThat(buyerAddress).isEmpty();
    }

    @Test
    void test_buyer_address_remove(){

        //----Arrange
        Buyer buyer = doTransaction(em -> {
            UserAccount account = new UserAccount("Cosmina", "Maria", "cosminamaria@gmail.com", "ozius1223423345", "/src/image2", "0735897635");
            Address address = new Address("Romania", "Timis", "Timisoara", "Strada Macilor 10", "Bloc 4, Scara F, ap 50", "300091");

            Buyer buyerToAdd = TestDataCreator.createBuyer(em, account);
            TestDataCreator.addAddressBuyer(em, buyerToAdd, address);

            return buyerToAdd;
        });


        //----Act
        doTransaction(em -> {
            Buyer mergedBuyer = em.merge(buyer);

            BuyerAddress addressToRemove = mergedBuyer.getAddresses().stream()
                    .findFirst()
                    .orElse(null);

            mergedBuyer.removeAddress(addressToRemove);
        });

        //----Assert
        Buyer persistedBuyer = entityFinder.getTheOne(Buyer.class);
        assertThat(persistedBuyer.getAddresses()).isEmpty();
    }
}
