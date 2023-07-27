package com.ozius.internship.project.entity;

import com.ozius.internship.project.TestDataCreator;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ozius.internship.project.TestDataCreator.Buyers.buyer;
import static com.ozius.internship.project.TestDataCreator.Orders.order;
import static com.ozius.internship.project.TestDataCreator.Products.product1;
import static com.ozius.internship.project.TestDataCreator.Products.product2;
import static com.ozius.internship.project.TestDataCreator.Reviews.review1;
import static com.ozius.internship.project.TestDataCreator.Reviews.review2;
import static com.ozius.internship.project.TestDataCreator.Sellers.seller;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;


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
                    "3003413");

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
        Seller persistedSeller = sellerRepository.findAll().get(0);
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
        assertThat(persistedSeller.getLegalAddress().getZipCode()).isEqualTo("3003413");
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

            //----SetNull
            Seller seller = em.merge(TestDataCreator.Sellers.seller);
            Order order = em.merge(TestDataCreator.Orders.order);

            List<Order> orderList = new SimpleJpaRepository<>(Order.class, em).findAll();
            Map<Seller, List<Order>> ordersBySeller = new HashMap<>();
            for(Order o : orderList){
                Seller sellerFound = o.getSeller();
                ordersBySeller.computeIfAbsent(sellerFound, k -> new ArrayList<>()).add(o);
            }
            for(Order o : ordersBySeller.get(seller)) {
                o.getOrderItems().forEach(OrderItem::setProductNull);
            }
            order.setSeller(null);

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
        assertThat(persistedReview.getBuyerInfo().getId()).isEqualTo(buyer.getId());
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

}
