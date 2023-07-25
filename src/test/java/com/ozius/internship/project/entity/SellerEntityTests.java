package com.ozius.internship.project.entity;

import com.ozius.internship.project.TestDataCreator;
import com.ozius.internship.project.repository.OrderRepository;
import com.ozius.internship.project.repository.ReviewRepository;
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

    @BeforeEach
    void setUp() {

    }

    @Test
    void test_add_seller(){
        Address address = new Address("Romania",
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

        assertThat(em.find(SellerInfo.class, 1l)).isEqualTo(seller);
    }

    @Test
    void test_update_seller(){
        TestDataCreator.createSellerBaseData(em);
        SellerInfo seller = em.find(SellerInfo.class, 1l);

        seller.updateFirstName("Vlad Cristian");

        assertThat(em.find(SellerInfo.class, 1l).getAccount().getFirstName()).isEqualTo("Vlad Cristian");
    }

    @Test
    void test_remove_seller(){
        TestDataCreator.createSellerBaseData(em);
        TestDataCreator.createCategoriesBaseData(em);
        TestDataCreator.createProductsBaseData(em);
        TestDataCreator.createBuyerBaseData(em);
        TestDataCreator.createAddressBaseData(em);
        TestDataCreator.createOrdersBaseData(em);
        TestDataCreator.createReviewBaseData(em);

        SellerInfo seller = em.find(SellerInfo.class, 1l);

        assertThat(em.find(Product.class, 1l)).isNotNull();
        assertThat(em.find(Product.class, 2l)).isNotNull();

        assertThat(em.find(Order.class, 1l).getSellerInfo()).isEqualTo(seller);

        assertThat(em.find(Review.class, 1l)).isNotNull();
        assertThat(em.find(Review.class, 2l)).isNotNull();

        List<Order> orderList = orderRepository.findAll();
        Map<SellerInfo, List<Order>> ordersBySeller = new HashMap<>();
        for(Order o : orderList){
            SellerInfo sellerFound = o.getSellerInfo();
            ordersBySeller.computeIfAbsent(sellerFound, k -> new ArrayList<>()).add(o);
        }
        for(Order o : ordersBySeller.get(seller)) {
            o.getOrderItems().forEach(OrderItem::setProductNull);
        }
        em.find(Order.class, 1l).setSellerInfo(null);
        em.remove(seller);
        em.flush();

        assertThat(em.find(SellerInfo.class, 1l)).isNull();
        assertThat(em.find(Product.class, 1l)).isNull();
        assertThat(em.find(Product.class, 2l)).isNull();

        assertThat(em.find(Order.class, 1l)).isNotNull();
        assertThat(em.find(Order.class, 1l).getSellerInfo()).isNull();

        assertThat(em.find(Review.class, 1l)).isNull();
        assertThat(em.find(Review.class, 2l)).isNull();
    }

    @Test
    void test_review_add(){
        TestDataCreator.createSellerBaseData(em);
        TestDataCreator.createBuyerBaseData(em);
        TestDataCreator.createCategoriesBaseData(em);
        TestDataCreator.createProductsBaseData(em);

        SellerInfo seller = em.find(SellerInfo.class, 1l);
        BuyerInfo buyer = em.find(BuyerInfo.class, 1l);
        Product product1 = em.find(Product.class, 1l);
        assertThat(buyer).isNotNull();
        assertThat(seller).isNotNull();
        assertThat(product1).isNotNull();

        seller.addReview(buyer, "very good", 4f, product1);
        em.flush();

//        em.find(SellerInfo.class, 1l).getReviews().forEach(System.out::println);
//        em.find(SellerInfo.class, 1l).getReviews().stream().findFirst().get().getId();
//        reviewRepository.findAll().forEach(System.out::println);
//        em.find(SellerInfo.class, 1l).getReviews().stream().map(Review::getId).forEach(System.out::println);

        assertThat(em.find(Review.class, 1l)).isNotNull();
    }

    @Test
    void test_review_update(){
        TestDataCreator.createSellerBaseData(em);
        TestDataCreator.createCategoriesBaseData(em);
        TestDataCreator.createProductsBaseData(em);
        TestDataCreator.createBuyerBaseData(em);
        TestDataCreator.createReviewBaseData(em);

        assertThat(em.find(Review.class, 1l)).isNotNull();
        assertThat(em.find(Review.class, 2l)).isNotNull();

        em.find(Review.class, 1l).updateDescription("very very bad bad review");

        assertThat(em.find(Review.class, 1l).getDescription()).isEqualTo("very very bad bad review");
    }

}
