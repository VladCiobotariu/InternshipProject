package com.ozius.internship.project.entity;

import com.ozius.internship.project.TestDataCreator;
import com.ozius.internship.project.entity.order.Order;
import com.ozius.internship.project.entity.order.OrderItem;
import com.ozius.internship.project.entity.seller.Seller;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import static com.ozius.internship.project.TestDataCreator.Buyers.buyer;
import static com.ozius.internship.project.TestDataCreator.Categories.category;
import static com.ozius.internship.project.TestDataCreator.Sellers.seller;
import static org.assertj.core.api.Assertions.assertThat;


public class OrderEntityTests extends EntityBaseTest{

    private JpaRepository<Order, Long> orderRepository;

    @Override
    public void createTestData(EntityManager em) {
        //----Arrange
        TestDataCreator.createBuyerBaseData(em);
        TestDataCreator.createSellerBaseData(em);
        TestDataCreator.createAddressBaseData(em);
        TestDataCreator.createCategoriesBaseData(em);
//        TestDataCreator.createProductsBaseData(em);

        this.orderRepository = new SimpleJpaRepository<>(Order.class, emb);
    }

    @Test
    void test_add_empty_order(){

        //----Act
        doTransaction(em -> {
            Buyer buyerMerged = em.merge(buyer);
            Seller sellerMerged = em.merge(seller);

            Order order = new Order(buyer.getAddresses().stream().findFirst().get().getAddress(), buyerMerged, sellerMerged, buyer.getAccount().getTelephone());

            em.persist(order);
        });

        //----Assert
        Order persistedOrder = new SimpleJpaRepository<>(Order.class, emb).findAll().get(0);

        assertThat(persistedOrder.getTotalPrice()).isEqualTo(0f);
        assertThat(persistedOrder.getOrderStatus()).isEqualTo(OrderStatus.DRAFT);
        assertThat(persistedOrder.getOrderItems().size()).isEqualTo(0);
    }

    @Test
    void test_add_items_to_order(){

        //----Act
        doTransaction(em -> {
            Product product1 = TestDataCreator.createProduct(em, "orez", "pentru fiert", "src/image4", 12f, category, seller);
            Product product2 = TestDataCreator.createProduct(em, "grau", "pentru paine", "src/image20", 8f, category, seller);

            Buyer buyerMerged = em.merge(buyer);
            Seller sellerMerged = em.merge(seller);

            Order order = new Order(buyer.getAddresses().stream().findFirst().get().getAddress(), buyerMerged, sellerMerged, buyer.getAccount().getTelephone());

            order.addProduct(product1, 1f);
            order.addProduct(product2, 2f);

            em.persist(order);
        });

        //----Assert
        Order persistedOrder = new SimpleJpaRepository<>(Order.class, emb).findAll().get(0);

        assertThat(persistedOrder.getTotalPrice()).isEqualTo(28.0f);
        assertThat(persistedOrder.getOrderStatus()).isEqualTo(OrderStatus.READY_TO_BE_PROCESSED);
        assertThat(persistedOrder.getOrderItems().size()).isEqualTo(2);
    }

    @Test
    void test_add_order_item_to_order(){

        //----Arrange
        Product product = doTransaction(em -> {
            return TestDataCreator.createProduct(em, "orez", "pentru fiert", "src/image4", 12f, category, seller);
        });

        //----Act
        OrderItem item = doTransaction(em -> {

            Buyer buyerMerged = em.merge(buyer);
            Seller sellerMerged = em.merge(seller);

            Order order = new Order(buyer.getAddresses().stream().findFirst().get().getAddress(), buyerMerged, sellerMerged, buyer.getAccount().getTelephone());

            OrderItem addedItem = order.addProduct(product, 2f);

            em.persist(order);

            return addedItem;
        });

        //----Assert
        Order persistedOrder = new SimpleJpaRepository<>(Order.class, emb).findAll().get(0);
        OrderItem persistedOrderItem = persistedOrder.getOrderItems().stream().findFirst().get();

        assertThat(persistedOrderItem).isEqualTo(item);
        assertThat(persistedOrderItem.getProduct()).isEqualTo(product);
    }

    @Test
    void test_update_product_details(){

        //----Arrange
        Product productToUpdate = doTransaction(em -> {

            Product product = TestDataCreator.createProduct(em, "orez", "pentru fiert", "src/image4", 12f, category, seller);

            Buyer buyerMerged = em.merge(buyer);
            Seller sellerMerged = em.merge(seller);

            Order order = new Order(buyer.getAddresses().stream().findFirst().get().getAddress(), buyerMerged, sellerMerged, buyer.getAccount().getTelephone());

            order.addProduct(product, 2f);

            em.persist(order);

            return product;
        });

        //----Act
        doTransaction(em -> {
            Product productMerged = em.merge(productToUpdate);
            productMerged.setPrice(50f);
        });

        //----Assert
        Order persistedOrder = new SimpleJpaRepository<>(Order.class, emb).findAll().get(0);
        OrderItem persistedOrderItem = persistedOrder.getOrderItems().stream().findFirst().orElseThrow();
        Product persistedProduct = persistedOrderItem.getProduct();

        assertThat(persistedOrderItem.getItemPrice()).isEqualTo(12f);
        assertThat(persistedProduct.getPrice()).isEqualTo(50f);
    }

    @Test
    void test_remove_order(){

        //----Arrange
        Order addedOrder = doTransaction(em -> {

            Product product = TestDataCreator.createProduct(em, "orez", "pentru fiert", "src/image4", 12f, category, seller);

            Buyer buyerMerged = em.merge(buyer);
            Seller sellerMerged = em.merge(seller);

            Order order = new Order(buyer.getAddresses().stream().findFirst().get().getAddress(), buyerMerged, sellerMerged, buyer.getAccount().getTelephone());

            order.addProduct(product, 2f);

            em.persist(order);

            return order;
        });

        //----Act
        doTransaction(em -> {
            Order orderMerged = em.merge(addedOrder);
            em.remove(orderMerged);
        });

        //----Assert
        assertThat(orderRepository.findAll().contains(addedOrder)).isFalse();
    }
}
