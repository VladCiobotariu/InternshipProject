package com.ozius.internship.project.entity;

import com.ozius.internship.project.TestDataCreator;
import com.ozius.internship.project.repository.OrderRepository;
import com.ozius.internship.project.repository.ReviewRepository;
import com.ozius.internship.project.repository.SellerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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

@DataJpaTest
public class SellerEntityTests {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @BeforeEach
    void setUp() {

    }

    @Test
    void test_add_seller(){

        //----Act
        Address address = new Address(
                "Romania",
                "Timis",
                "Timisoara",
                "Strada Circumvalatiunii nr 4",
                "Bloc 3 Scara B Ap 12",
                "3003413");

        SellerInfo seller = new SellerInfo(
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

        em.flush();
        em.clear();

        //----Assert
        SellerInfo persistedSeller = sellerRepository.findById(seller.getId()).orElseThrow();
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
        TestDataCreator.createSellerBaseData(em);

        //----Act
        seller.updateFirstName("Vlad Cristian");

        em.flush();
        em.clear();

        //----Assert
        SellerInfo persistedSeller = sellerRepository.findById(seller.getId()).orElseThrow();
        assertThat(persistedSeller.getAccount().getFirstName()).isEqualTo("Vlad Cristian");
    }

    @Test
    void test_remove_seller(){

        //----Arrange
        TestDataCreator.createSellerBaseData(em);
        TestDataCreator.createCategoriesBaseData(em);
        TestDataCreator.createProductsBaseData(em);
        TestDataCreator.createBuyerBaseData(em);
        TestDataCreator.createAddressBaseData(em);
        TestDataCreator.createOrdersBaseData(em);
        TestDataCreator.createReviewBaseData(em);

        //----SetNull
        List<Order> orderList = orderRepository.findAll();
        Map<SellerInfo, List<Order>> ordersBySeller = new HashMap<>();
        for(Order o : orderList){
            SellerInfo sellerFound = o.getSellerInfo();
            ordersBySeller.computeIfAbsent(sellerFound, k -> new ArrayList<>()).add(o);
        }
        for(Order o : ordersBySeller.get(seller)) {
            o.getOrderItems().forEach(OrderItem::setProductNull);
        }
        order.setSellerInfo(null);

        //----Act
        em.remove(seller);

        em.flush();
        em.clear();

        //----Assert
        SellerInfo persistedSeller = em.find(SellerInfo.class, seller.getId());
        Product persistedProduct1 = em.find(Product.class, product1.getId());
        Product persistedProduct2 = em.find(Product.class, product2.getId());
        Order persistedOrder = em.find(Order.class, order.getId());
        Review persistedReview1 = em.find(Review.class, review1.getId());
        Review persistedReview2 = em.find(Review.class, review2.getId());
        assertThat(persistedSeller).isNull();
        assertThat(persistedProduct1).isNull();
        assertThat(persistedProduct2).isNull();

        assertThat(persistedOrder).isNotNull();
        assertThat(persistedOrder.getSellerInfo()).isNull();

        assertThat(persistedReview1).isNull();
        assertThat(persistedReview2).isNull();
    }

    @Test
    void test_review_add(){

        //----Arrange
        TestDataCreator.createSellerBaseData(em);
        TestDataCreator.createBuyerBaseData(em);
        TestDataCreator.createCategoriesBaseData(em);
        TestDataCreator.createProductsBaseData(em);

        //----Act
        Review review = seller.addReview(buyer, "very good", 4f, product1);

        em.flush();
        em.clear();

        //----Assert
        Review persistedReview = em.find(Review.class, review.getId());
        assertThat(persistedReview).isNotNull();
        assertThat(persistedReview.getBuyerInfo()).isEqualTo(buyer);
        assertThat(persistedReview.getDescription()).isEqualTo("very good");
        assertThat(persistedReview.getRating()).isEqualTo(4f);
        assertThat(persistedReview.getProduct()).isEqualTo(product1);
    }

    @Test
    void test_review_update(){

        //----Arrange
        TestDataCreator.createSellerBaseData(em);
        TestDataCreator.createCategoriesBaseData(em);
        TestDataCreator.createProductsBaseData(em);
        TestDataCreator.createBuyerBaseData(em);
        TestDataCreator.createReviewBaseData(em);

        //----Act
        review1.updateDescription("very very bad bad review");

        em.flush();
        em.clear();

        //----Assert
        Review persistedReview1 = reviewRepository.findById(review1.getId()).orElseThrow();
        assertThat(persistedReview1.getDescription()).isEqualTo("very very bad bad review");
    }

}
