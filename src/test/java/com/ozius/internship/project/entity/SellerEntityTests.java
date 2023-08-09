package com.ozius.internship.project.entity;

import com.ozius.internship.project.TestDataCreator;
import com.ozius.internship.project.entity.order.Order;
import com.ozius.internship.project.entity.seller.Review;
import com.ozius.internship.project.entity.seller.Seller;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.util.List;

import static com.ozius.internship.project.TestDataCreator.Buyers.buyer;
import static com.ozius.internship.project.TestDataCreator.Products.product1;
import static com.ozius.internship.project.TestDataCreator.Sellers.seller;
import static org.assertj.core.api.Assertions.assertThat;


public class SellerEntityTests extends EntityBaseTest{

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
                    "Mega Fresh SRL"
            );
            em.persist(seller);

            return seller;
        });

        //TODO is it ok to verify every field with hard coded strings?
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

            return TestDataCreator.createSeller(em, address, userAccount, "honey srl");
        });

        //----Act
        Seller updatedSeller = doTransaction(em -> {
            Seller mergedSeller = em.merge(seller);
            UserAccount account = mergedSeller.getAccount();
            mergedSeller.updateSeller(
                    account.getEmail(),
                    "Vlad Cristian",
                    account.getLastName(),
                    account.getPasswordHash(),
                    account.getImageName(),
                    account.getTelephone());

            return mergedSeller;
        });

        //----Assert
        Seller persistedSeller = entityFinder.getTheOne(Seller.class);
        assertThat(persistedSeller.getAccount().getFirstName()).isEqualTo("Vlad Cristian");
        assertThat(persistedSeller).isEqualTo(updatedSeller);
        assertThat(persistedSeller).isNotSameAs(seller);
    }

    @Test
    void test_remove_seller(){

        //----Arrange
        Order order = doTransaction(em -> {
            TestDataCreator.createSellerBaseData(em);
            TestDataCreator.createBuyerBaseData(em);
            TestDataCreator.createAddressBaseData(em); //needed for order to be created

            return TestDataCreator.createOrder(em, buyer, seller);
        });


        //----Act
        Seller removedSeller = doTransaction(em -> {

            Seller seller = em.merge(TestDataCreator.Sellers.seller);
            em.remove(seller);

            return seller;
        });

        //----Assert
        List<Product> persistedProducts = entityFinder.getProductsBySeller(removedSeller);
        Order persistedOrder = entityFinder.getTheOne(Order.class);

        assertThat(sellerRepository.findAll().contains(removedSeller)).isFalse();
        assertThat(persistedProducts).isEmpty();

        assertThat(persistedOrder).isNotNull();
        assertThat(persistedOrder.getSeller()).isNull();
        assertThat(persistedOrder).isEqualTo(order);

        //TODO check reviews deleted? it has cascade.all and also is it necessary to check products deleted? it also has cascade.all
    }

    @Test
    void test_review_add(){

        //----Arrange
        doTransaction(em -> {
            TestDataCreator.createSellerBaseData(em);
            TestDataCreator.createBuyerBaseData(em);
            TestDataCreator.createCategoriesBaseData(em);
            TestDataCreator.createProductsBaseData(em);
        });

        //----Act
        Review addedReview = doTransaction(em -> {
            Buyer mergedBuyer = em.merge(buyer);
            Product mergedProduct = em.merge(product1);
            Seller mergedSeller = em.merge(seller);

            return mergedSeller.addReview(mergedBuyer, "very good", 4f, mergedProduct);
        });

        //----Assert
        Review persistedReview = entityFinder.getTheOne(Review.class);

        assertThat(persistedReview).isNotNull();
        assertThat(persistedReview).isEqualTo(addedReview);
        assertThat(persistedReview.getBuyer()).isEqualTo(buyer);
        assertThat(persistedReview.getDescription()).isEqualTo("very good");
        assertThat(persistedReview.getRating()).isEqualTo(4f);
        assertThat(persistedReview.getProduct()).isEqualTo(product1);
    }

    @Test
    void test_review_update(){

        //----Arrange
        Review review = doTransaction(em -> {
            TestDataCreator.createSellerBaseData(em);
            TestDataCreator.createCategoriesBaseData(em);
            TestDataCreator.createProductsBaseData(em);
            TestDataCreator.createBuyerBaseData(em);

            return TestDataCreator.createReview(em, buyer, "good review", 4.5f, product1);
        });


        //----Act
        Review updatedReview = doTransaction(em -> {
            Review mergedReview = em.merge(review);
            mergedReview.updateReview("very very bad bad review", mergedReview.getRating(), mergedReview.getBuyer(), mergedReview.getProduct());

            return mergedReview;
        });

        //----Assert
        Review persistedReview = entityFinder.getTheOne(Review.class);

        assertThat(persistedReview.getDescription()).isEqualTo("very very bad bad review");
        assertThat(persistedReview).isEqualTo(updatedReview);
        assertThat(persistedReview).isNotSameAs(review);
    }

    @Test
    void show_products(){

        //----Arrange
        doTransaction(em -> {
            TestDataCreator.createSellerBaseData(em);
            TestDataCreator.createCategoriesBaseData(em);
            TestDataCreator.createProductsBaseData(em);
        });

        Seller seller = entityFinder.getTheOne(Seller.class);
        List<Product> products = entityFinder.getProductsBySeller(seller);

        assertThat(products.size()).isEqualTo(2);
    }
}
