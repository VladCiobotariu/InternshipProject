package com.ozius.internship.project.entity;

import com.ozius.internship.project.TestDataCreator;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.test.annotation.DirtiesContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.ozius.internship.project.TestDataCreator.Buyers.buyer;
import static com.ozius.internship.project.TestDataCreator.OrderItems.orderItem1;
import static com.ozius.internship.project.TestDataCreator.Orders.order;
import static com.ozius.internship.project.TestDataCreator.Sellers.seller;
import static com.ozius.internship.project.TestDataCreator.Products.product1;
import static com.ozius.internship.project.TestDataCreator.Products.product2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;


public class OrderEntityTests extends EntityBaseTest{

    private JpaRepository<Order, Long> orderRepository;

    @Override
    public void createTestData(EntityManager em) {
        //----Arrange
        TestDataCreator.createBuyerBaseData(em);
        TestDataCreator.createSellerBaseData(em);
        TestDataCreator.createAddressBaseData(em);
        TestDataCreator.createCategoriesBaseData(em);
        TestDataCreator.createProductsBaseData(em);

        this.orderRepository = new SimpleJpaRepository<>(Order.class, emb);
    }

    @Test
    void test_add_order(){

        //----Arrange
        OrderItem orderItem1 = new OrderItem(product1, 5f);
        OrderItem orderItem2 = new OrderItem(product2, 1f);

        Set<OrderItem> items = new HashSet<>();
        items.add(orderItem1);
        items.add(orderItem2);

        //----Act
        doTransaction(em -> {
            Buyer buyerMerged = em.merge(buyer);
            Seller sellerMerged = em.merge(seller);
            Order order = new Order(
                    buyer.getAddresses().stream().findFirst().get().getAddress(),
                    buyerMerged,
                    sellerMerged,
                    buyer.getAccount().getTelephone(),
                    items);
            em.persist(order);
        });

        //----Assert
        Order persistedOrder = new SimpleJpaRepository<>(Order.class, emb).findAll().get(0);
        assertThat(persistedOrder.getTotalPrice()).isEqualTo(71.7f);
        assertThat(persistedOrder.getOrderStatus()).isEqualTo(OrderStatus.RECEIVED);
        assertThat(persistedOrder.getOrderItems().size()).isEqualTo(2);
        assertThat(persistedOrder.getOrderItems()).extracting(BaseEntity::getId).containsAnyOf(orderItem1.getId());
        assertThat(persistedOrder.getOrderItems()).extracting(BaseEntity::getId).containsAnyOf(orderItem2.getId());
        assertThat(persistedOrder.getOrderItems().stream().filter(orderItem -> orderItem.getId() == orderItem1.getId()).findFirst().orElseThrow().getName()).isEqualTo("orez");
        assertThat(persistedOrder.getOrderItems().stream().filter(orderItem -> orderItem.getId() == orderItem2.getId()).findFirst().orElseThrow().getName()).isEqualTo("grau");
    }

    @Test
    void test_add_order_item_to_order(){

        //----Arrange
        doTransaction(em -> {
            TestDataCreator.createOrdersBaseData(em);
        });

        //----Assert
        Order persistedOrder = new SimpleJpaRepository<>(Order.class, emb).findAll().get(0);
        OrderItem persistedOrderItem = persistedOrder.getOrderItems().stream().filter(orderItem -> orderItem.getProduct().getId() == product1.getId()).findFirst().get();

        assertThat(persistedOrderItem.getId()).isEqualTo(orderItem1.getId());
        assertThat(persistedOrderItem.getName()).isEqualTo("orez");
        assertThat(persistedOrderItem.getPrice() ).isEqualTo(12.7f);
        assertThat(persistedOrderItem.getDescription() ).isEqualTo("pentru fiert");
        assertThat(persistedOrderItem.getQuantity()).isEqualTo(5f);
    }

    @Test
    void test_update_product_details(){
        //----Arrange
        doTransaction(em -> {
            TestDataCreator.createOrdersBaseData(em);
        });

        //----Act
        doTransaction(em -> {
            Product product = new SimpleJpaRepository<>(Product.class, em).findAll().get(0);
            product.setPrice(50f);
        });

        //----Assert
        Order persistedOrder = new SimpleJpaRepository<>(Order.class, emb).findAll().get(0);
        assertThat(persistedOrder.getOrderItems().stream().filter(orderItem -> orderItem.getProduct().getId() == product1.getId()).findFirst().get().getPrice()).isEqualTo(12.7f);
        assertThat(persistedOrder.getOrderItems().stream().filter(orderItem -> orderItem.getProduct().getId() == product1.getId()).findFirst().orElseThrow().getProduct().getPrice()).isEqualTo(50f);
    }

    @Test
    void test_remove_order(){

        //----Arrange
        doTransaction(em -> {
            TestDataCreator.createOrdersBaseData(em);
        });

        //----Act
        Order remmovedOrder = doTransaction(em -> {
            Order removeOrder = new SimpleJpaRepository<>(Order.class, em).findAll().get(0);
            em.remove(removeOrder);
            return removeOrder;
        });

        //----Assert
        List<Order> persistedOrder = orderRepository.findAll();
        assertThat(persistedOrder).isEmpty();
    }
}
