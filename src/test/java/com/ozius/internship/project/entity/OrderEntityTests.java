package com.ozius.internship.project.entity;

import com.ozius.internship.project.TestDataCreator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@DataJpaTest
public class OrderEntityTests {

    @PersistenceContext
    private EntityManager em;

    @BeforeEach
    void setUp() {
        TestDataCreator.createBuyerBaseData(em);
        TestDataCreator.createSellerBaseData(em);
        TestDataCreator.createAddressBaseData(em);
        TestDataCreator.createCategoriesBaseData(em);
        TestDataCreator.createProductsBaseData(em);
    }

    @Test
    void test_add_order(){
        BuyerInfo buyer = em.find(BuyerInfo.class, 1l);
        SellerInfo seller = em.find(SellerInfo.class, 1l);

        Address address = buyer.getAddresses().stream().findFirst().get().getAddress();
        Product product1 = em.find(Product.class, 1l);
        Product product2 = em.find(Product.class, 2l);

        OrderItem orderItem1 = new OrderItem(product1, 5f);
        OrderItem orderItem2 = new OrderItem(product2, 1f);

        Set<OrderItem> items = new HashSet<>();
        items.add(orderItem1);
        items.add(orderItem2);

        Order order = new Order(address, buyer, seller, buyer.getAccount().getTelephone(), items);
        em.persist(order);

        assertThat(em.find(Order.class, 1l).getTotalPrice()).isEqualTo(71.7f);
        assertThat(em.find(Order.class, 1l).getOrderItems()).isEqualTo(items);
        assertThat(em.find(Order.class, 1l).getOrderStatus()).isEqualTo(OrderStatus.RECEIVED);
        assertThat(em.find(Order.class, 1l).getOrderItems().contains(em.find(OrderItem.class, 1l))).isTrue();
        assertThat(em.find(Order.class, 1l).getOrderItems().stream().filter(orderItem -> orderItem.getId() == 1l).findFirst().get().getName()).isEqualTo("grau");
        assertThat(em.find(Order.class, 1l).getOrderItems().stream().filter(orderItem -> orderItem.getId() == 2l).findFirst().get().getName()).isEqualTo("orez");

    }

    @Test
    void test_update_order(){
        TestDataCreator.createOrdersBaseData(em);
        Order order = em.find(Order.class, 1l);

        System.out.println(order.getTotalPrice());
    }

    @Test
    void test_remove_order(){
        TestDataCreator.createOrdersBaseData(em);
        Order order = em.find(Order.class, 1l);

//        order.getOrderItems().stream().map(OrderItem::getId).forEach(System.out::println);

        em.remove(order);
        em.flush();

        assertThat(em.find(Order.class, 1l)).isNull();
        assertThat(em.find(OrderItem.class, 1l)).isNull();
        assertThat(em.find(OrderItem.class, 2l)).isNull();
    }
}
