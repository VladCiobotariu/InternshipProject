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

import static com.ozius.internship.project.TestDataCreator.Buyers.buyer;
import static com.ozius.internship.project.TestDataCreator.OrderItems.orderItem1;
import static com.ozius.internship.project.TestDataCreator.OrderItems.orderItem2;
import static com.ozius.internship.project.TestDataCreator.Orders.order;
import static com.ozius.internship.project.TestDataCreator.Sellers.seller;
import static com.ozius.internship.project.TestDataCreator.Products.product1;
import static com.ozius.internship.project.TestDataCreator.Products.product2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@DataJpaTest
public class OrderEntityTests {

    @PersistenceContext
    private EntityManager em;

    @BeforeEach
    void setUp() {

        //----Arrange
        TestDataCreator.createBuyerBaseData(em);
        TestDataCreator.createSellerBaseData(em);
        TestDataCreator.createAddressBaseData(em);
        TestDataCreator.createCategoriesBaseData(em);
        TestDataCreator.createProductsBaseData(em);

    }

    @Test
    void test_add_order(){

        //----Arrange
        Address address = buyer.getAddresses().stream().findFirst().get().getAddress();

        OrderItem orderItem1 = new OrderItem(product1, 5f);
        OrderItem orderItem2 = new OrderItem(product2, 1f);

        Set<OrderItem> items = new HashSet<>();
        items.add(orderItem1);
        items.add(orderItem2);

        //----Act
        Order order = new Order(address, buyer, seller, buyer.getAccount().getTelephone(), items);
        em.persist(order);

        em.flush();
        em.clear();

        //----Assert
        Order persistedOrder = em.find(Order.class, order.getId());
        assertThat(persistedOrder.getTotalPrice()).isEqualTo(63.5f);
        assertThat(persistedOrder.getOrderItems()).isEqualTo(items);
        assertThat(persistedOrder.getOrderStatus()).isEqualTo(OrderStatus.RECEIVED);
        assertThat(persistedOrder.getOrderItems()).extracting(BaseEntity::getId).containsExactlyInAnyOrder(orderItem1.getId());
//        assertThat(persistedOrder.getOrderItems()).containsExactlyInAnyOrder(orderItem2);
        assertThat(persistedOrder.getOrderItems().stream().filter(orderItem -> orderItem.getId() == orderItem1.getId()).findFirst().orElseThrow().getName()).isEqualTo("orez");
        assertThat(persistedOrder.getOrderItems().stream().filter(orderItem -> orderItem.getId() == orderItem2.getId()).findFirst().orElseThrow().getName()).isEqualTo("grau");

    }

    @Test
    void test_remove_order(){

        //----Arrange
        TestDataCreator.createOrdersBaseData(em);

        //----Act
        em.remove(order);

        em.flush();
        em.clear();

        //----Assert
        Order persistedOrder = em.find(Order.class, order.getId());
        OrderItem persistedOrderItem1 = em.find(OrderItem.class, orderItem1.getId());
        OrderItem persistedOrderItem2 = em.find(OrderItem.class, orderItem2.getId());
        assertThat(persistedOrder).isNull();
        assertThat(persistedOrderItem1).isNull();
        assertThat(persistedOrderItem2).isNull();
    }
}
