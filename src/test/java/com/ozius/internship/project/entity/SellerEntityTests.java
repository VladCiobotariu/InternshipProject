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
import static org.assertj.core.api.Assertions.assertThat;


public class SellerEntityTests extends EntityBaseTest{

    private JpaRepository<Seller, Long> sellerRepository;

    private JpaRepository<Order, Long> orderRepository;

    @Override
    public void createTestData(EntityManager em) {
        this.sellerRepository = new SimpleJpaRepository<>(Seller.class, emb);
        this.orderRepository = new SimpleJpaRepository<>(Order.class, emb);
    }

    @Test
    void test_add_seller(){

        //----Act
        doTransaction(em -> {
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
    }

    @Test
    void test_update_seller(){

        //----Arrange
        doTransaction(em -> {
            TestDataCreator.createSellerBaseData(em);
        });

        //----Act
        doTransaction(em -> {
            Seller seller = em.merge(TestDataCreator.Sellers.seller);
            seller.updateFirstName("Vlad Cristian");
        });

        //----Assert
        Seller persistedSeller = sellerRepository.findAll().get(0);
        assertThat(persistedSeller.getAccount().getFirstName()).isEqualTo("Vlad Cristian");
    }

    @Test
    void test_remove_seller(){

        //----Arrange
        doTransaction(em -> {
            TestDataCreator.createSellerBaseData(em);
            TestDataCreator.createCategoriesBaseData(em);
            TestDataCreator.createProductsBaseData(em);
            TestDataCreator.createBuyerBaseData(em);
            TestDataCreator.createAddressBaseData(em);
            TestDataCreator.createOrdersBaseData(em);
            TestDataCreator.createReviewBaseData(em);
        });


        //----Act
        doTransaction(em -> {

            Seller seller = em.merge(TestDataCreator.Sellers.seller);
            em.remove(seller);

        });

        //----Assert
        List<Seller> persistedSeller = sellerRepository.findAll();
        List<Product> persistedProducts = new SimpleJpaRepository<>(Product.class, emb).findAll();
        Order persistedOrder = new SimpleJpaRepository<>(Order.class, emb).findAll().get(0);
        List<Review> persistedReviews = new SimpleJpaRepository<>(Review.class, emb).findAll();
        assertThat(persistedSeller).isEmpty();
        assertThat(persistedProducts).isEmpty();

        assertThat(persistedOrder).isNotNull();
        assertThat(persistedOrder.getSeller()).isNull();

        assertThat(persistedReviews).isEmpty();
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

        doTransaction(em -> {
            Buyer buyer = em.merge(TestDataCreator.Buyers.buyer);
            Product product1 = em.merge(TestDataCreator.Products.product1);
            Seller seller = em.merge(TestDataCreator.Sellers.seller);
            Review review = seller.addReview(buyer, "very good", 4f, product1);
        });

        //----Assert
        Review persistedReview = new SimpleJpaRepository<>(Review.class, emb).findAll().get(0);
        assertThat(persistedReview).isNotNull();
        assertThat(persistedReview.getBuyer()).isEqualTo(buyer);
        assertThat(persistedReview.getDescription()).isEqualTo("very good");
        assertThat(persistedReview.getRating()).isEqualTo(4f);
        assertThat(persistedReview.getProduct().getId()).isEqualTo(product1.getId());
    }

    @Test
    void test_review_update(){

        //----Arrange
        doTransaction(em -> {
            TestDataCreator.createSellerBaseData(em);
            TestDataCreator.createCategoriesBaseData(em);
            TestDataCreator.createProductsBaseData(em);
            TestDataCreator.createBuyerBaseData(em);
            TestDataCreator.createReviewBaseData(em);
        });


        //----Act
        doTransaction(em -> {
            Review review1 = em.merge(TestDataCreator.Reviews.review1);
            review1.updateDescription("very very bad bad review");
        });

        //----Assert
        Review persistedReview1 = new SimpleJpaRepository<>(Review.class, emb).findAll().get(0);
        assertThat(persistedReview1.getDescription()).isEqualTo("very very bad bad review");
    }

    @Test
    void show_products(){

        //----Arrange
        doTransaction(em -> {
            TestDataCreator.createSellerBaseData(em);
            TestDataCreator.createCategoriesBaseData(em);
            TestDataCreator.createProductsBaseData(em);
        });

        Seller seller = sellerRepository.findAll().get(0);
        List<Product> products = entityFinder.getProductsBySeller(seller);

        assertThat(products.size()).isEqualTo(2);
    }
}
