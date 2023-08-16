package com.ozius.internship.project.entity.seller;

import com.ozius.internship.project.TestDataCreator;
import com.ozius.internship.project.entity.*;
import com.ozius.internship.project.entity.order.Order;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.util.List;

import static com.ozius.internship.project.TestDataCreator.Buyers.buyer1;

import static org.assertj.core.api.Assertions.assertThat;


public class SellerEntityTests extends EntityBaseTest {

    private JpaRepository<Seller, Long> sellerRepository;

    @Override
    public void createTestData(EntityManager em) {
        this.sellerRepository = new SimpleJpaRepository<>(Seller.class, emb);
    }

    @Test
    void test_add_seller(){

        //----Act
        Seller addedSeller = doTransaction(em -> {
            Address address = new Address(
                    "Romania",
                    "Timis",
                    "Timisoara",
                    "Strada Circumvalatiunii nr 4",
                    "Bloc 3 Scara B Ap 12",
                    "303413");

            Seller seller = new Seller(
                    address,
                    new UserAccount(
                            "Vlad",
                            "Ciobotariu",
                            "vladciobotariu@gmail.com",
                            "ozius12345",
                            "/src/image1",
                            "0734896512"
                    ),
                    "Mega Fresh SRL",
                    SellerType.LOCAL_FARMER,
                    null
            );
            em.persist(seller);

            return seller;
        });

        //----Assert
        Seller persistedSeller = entityFinder.getTheOne(Seller.class);

        assertThat(persistedSeller.getAccount().getFirstName()).isEqualTo("Vlad");
        assertThat(persistedSeller.getAccount().getLastName()).isEqualTo("Ciobotariu");
        assertThat(persistedSeller.getAccount().getEmail()).isEqualTo("vladciobotariu@gmail.com");
        assertThat(persistedSeller.getAccount().getPasswordHash()).isEqualTo("ozius12345");
        assertThat(persistedSeller.getAccount().getImageName()).isEqualTo("/src/image1");
        assertThat(persistedSeller.getAccount().getTelephone()).isEqualTo("0734896512");
        assertThat(persistedSeller.getAlias()).isEqualTo("Mega Fresh SRL");
        assertThat(persistedSeller.getLegalAddress().getCountry()).isEqualTo("Romania");
        assertThat(persistedSeller.getLegalAddress().getState()).isEqualTo("Timis");
        assertThat(persistedSeller.getLegalAddress().getCity()).isEqualTo("Timisoara");
        assertThat(persistedSeller.getLegalAddress().getAddressLine1()).isEqualTo("Strada Circumvalatiunii nr 4");
        assertThat(persistedSeller.getLegalAddress().getAddressLine2()).isEqualTo("Bloc 3 Scara B Ap 12");
        assertThat(persistedSeller.getLegalAddress().getZipCode()).isEqualTo("303413");
        assertThat(persistedSeller.getSellerType()).isEqualTo(SellerType.LOCAL_FARMER);
        assertThat(persistedSeller.getLegalDetails()).isNull();

        assertThat(persistedSeller).isEqualTo(addedSeller);
        assertThat(persistedSeller.getReviews()).isEmpty();
        assertThat(persistedSeller.calculateRating()).isEqualTo(0);
    }

    @Test
    void test_update_seller(){

        //----Arrange
        Seller seller = doTransaction(em -> {

            Address address = new Address("Romania", "Timis", "Timisoara", "Strada Circumvalatiunii nr 4", "Bloc 3 Scara B Ap 12", "303413");
            UserAccount userAccount = new UserAccount("Vlad", "Ciobotariu", "vladciobotariu@gmail.com", "ozius12345", "/src/image1", "0734896512");

            return TestDataCreator.createSeller(em, address, userAccount, "honey srl", SellerType.LOCAL_FARMER, null);
        });

        //----Act
        doTransaction(em -> {
            Seller mergedSeller = em.merge(seller);
            UserAccount account = mergedSeller.getAccount();
            mergedSeller.updateSeller(
                    "Vlad Cristian",
                    account.getLastName(),
                    account.getEmail(),
                    account.getPasswordHash(),
                    account.getImageName(),
                    account.getTelephone());
        });

        //----Assert
        Seller persistedSeller = entityFinder.getTheOne(Seller.class);
        assertThat(persistedSeller.getAccount().getFirstName()).isEqualTo("Vlad Cristian");

        assertThat(persistedSeller.getAccount().getLastName()).isEqualTo("Ciobotariu");
        assertThat(persistedSeller.getAccount().getEmail()).isEqualTo("vladciobotariu@gmail.com");
        assertThat(persistedSeller.getAccount().getPasswordHash()).isEqualTo("ozius12345");
        assertThat(persistedSeller.getAccount().getImageName()).isEqualTo("/src/image1");
        assertThat(persistedSeller.getAccount().getTelephone()).isEqualTo("0734896512");
        assertThat(persistedSeller.getAlias()).isEqualTo("honey srl");
        assertThat(persistedSeller.getLegalAddress().getCountry()).isEqualTo("Romania");
        assertThat(persistedSeller.getLegalAddress().getState()).isEqualTo("Timis");
        assertThat(persistedSeller.getLegalAddress().getCity()).isEqualTo("Timisoara");
        assertThat(persistedSeller.getLegalAddress().getAddressLine1()).isEqualTo("Strada Circumvalatiunii nr 4");
        assertThat(persistedSeller.getLegalAddress().getAddressLine2()).isEqualTo("Bloc 3 Scara B Ap 12");
        assertThat(persistedSeller.getLegalAddress().getZipCode()).isEqualTo("303413");

    }

    @Test
    void test_remove_seller(){

        //----Arrange
        Seller sellerToAdd = doTransaction(em -> {
            Address addressSeller = new Address("Romania", "Timis", "Timisoara", "Strada Circumvalatiunii nr 4", "Bloc 3 Scara B Ap 12", "303413");
            UserAccount userAccount = new UserAccount("Vlad", "Ciobotariu", "vladciobotariu@gmail.com", "ozius12345", "/src/image1", "0734896512");

            Seller seller = TestDataCreator.createSeller(em, addressSeller, userAccount, "bio", SellerType.LOCAL_FARMER, null);

            TestDataCreator.createBuyerBaseData(em);
            Address addressBuyer = new Address("Romania", "Timis", "Timisoara", "Strada Macilor 10", "Bloc 4, Scara F, ap 50", "300091");

            TestDataCreator.createOrder(em,addressBuyer, buyer1, seller);

            return seller;
        });


        //----Act
        Seller removedSeller = doTransaction(em -> {

            Seller mergedSeller = em.merge(sellerToAdd);
            em.remove(mergedSeller);

            return mergedSeller;
        });

        //----Assert
        List<Product> persistedProducts = entityFinder.getProductsBySeller(removedSeller);
        Order persistedOrder = entityFinder.getTheOne(Order.class);

        assertThat(sellerRepository.findAll().contains(removedSeller)).isFalse();
        assertThat(persistedProducts).isEmpty();

        assertThat(persistedOrder).isNotNull();
        assertThat(persistedOrder.getSeller()).isNull();

        //TODO check reviews deleted? it has cascade.all and also is it necessary to check products deleted? it also has cascade.all
    }

}
