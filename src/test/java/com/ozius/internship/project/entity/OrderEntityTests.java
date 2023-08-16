package com.ozius.internship.project.entity;

import com.ozius.internship.project.TestDataCreator;
import com.ozius.internship.project.entity.buyer.Buyer;
import com.ozius.internship.project.entity.exeption.IllegalItemException;
import com.ozius.internship.project.entity.exeption.IllegalOrderState;
import com.ozius.internship.project.entity.order.Order;
import com.ozius.internship.project.entity.order.OrderItem;
import com.ozius.internship.project.entity.order.OrderStatus;
import com.ozius.internship.project.entity.seller.Seller;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.time.LocalDate;

import static com.ozius.internship.project.TestDataCreator.Addresses.address1;
import static com.ozius.internship.project.TestDataCreator.Buyers.buyer1;
import static com.ozius.internship.project.TestDataCreator.Categories.category1;
import static com.ozius.internship.project.TestDataCreator.Products.product1;
import static com.ozius.internship.project.TestDataCreator.Products.product3;
import static com.ozius.internship.project.TestDataCreator.Sellers.seller1;
import static com.ozius.internship.project.TestDataCreator.Sellers.seller2;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderEntityTests extends EntityBaseTest{

    private JpaRepository<Order, Long> orderRepository;

    @Override
    public void createTestData(EntityManager em) {
        //----Arrange
        TestDataCreator.createBuyerBaseData(em);
        TestDataCreator.createSellerBaseData(em);
        TestDataCreator.createCategoriesBaseData(em);

        this.orderRepository = new SimpleJpaRepository<>(Order.class, emb);
    }

    @Test
    void test_add_empty_order(){

        //----Act
        Order addedOrder = doTransaction(em -> {
            Buyer buyerMerged = em.merge(buyer1);
            Seller sellerMerged = em.merge(seller1);
            Address address = new Address("Romania", "Timis", "Timisoara", "Strada Macilor 10", "Bloc 4, Scara F, ap 50", "300091");

            Order order = new Order(address, buyerMerged, sellerMerged, buyer1.getAccount().getTelephone());

            em.persist(order);

            return order;
        });
        Address addedAddress = addedOrder.getAddress();

        //----Assert
        Order persistedOrder = entityFinder.getTheOne(Order.class);

        assertThat(persistedOrder).isEqualTo(addedOrder);
        assertThat(persistedOrder.getTotalPrice()).isEqualTo(0f);
        assertThat(persistedOrder.getOrderStatus()).isEqualTo(OrderStatus.DRAFT);
        assertThat(persistedOrder.getOrderItems().size()).isEqualTo(0);
        assertThat(persistedOrder.getBuyer()).isEqualTo(buyer1);
        assertThat(persistedOrder.getOrderDate().toLocalDate().isEqual(LocalDate.now())).isTrue();
        assertThat(persistedOrder.getBuyerEmail()).isEqualTo(buyer1.getAccount().getEmail());
        assertThat(persistedOrder.getSeller()).isEqualTo(seller1);
        assertThat(persistedOrder.getTelephone()).isEqualTo(buyer1.getAccount().getTelephone());
        assertThat(persistedOrder.getAddress()).isEqualTo(addedAddress);
        assertThat(persistedOrder.getSellerEmail()).isEqualTo(seller1.getAccount().getEmail());
    }

    @Test
    void test_add_items_to_order(){

        //----Act
        doTransaction(em -> {

            TestDataCreator.createAddresses();

            Product product1 = TestDataCreator.createProduct(em, "orez", "pentru fiert", "src/image4", 12f, category1, seller1);
            Product product2 = TestDataCreator.createProduct(em, "grau", "pentru paine", "src/image20", 8f, category1, seller1);

            Buyer buyerMerged = em.merge(buyer1);
            Seller sellerMerged = em.merge(seller1);

            Order order = new Order(address1, buyerMerged, sellerMerged, buyer1.getAccount().getTelephone());

            order.addProduct(product1, 1f);
            order.addProduct(product2, 2f);

            em.persist(order);
        });

        //----Assert
        Order persistedOrder = entityFinder.getTheOne(Order.class);

        assertThat(persistedOrder.getTotalPrice()).isEqualTo(28.0f);
        assertThat(persistedOrder.getOrderItems().size()).isEqualTo(2);
    }

    @Test
    void test_add_order_item_to_order(){

        //----Arrange
        Product product = doTransaction(em -> {
            TestDataCreator.createAddresses();
            return TestDataCreator.createProduct(em, "orez", "pentru fiert", "src/image4", 12f, category1, seller1);
        });

        //----Act
        OrderItem item = doTransaction(em -> {

            Buyer buyerMerged = em.merge(buyer1);
            Seller sellerMerged = em.merge(seller1);

            Order order = new Order(address1, buyerMerged, sellerMerged, buyer1.getAccount().getTelephone());

            OrderItem addedItem = order.addProduct(product, 2f);

            em.persist(order);

            return addedItem;
        });

        //----Assert
        Order persistedOrder = entityFinder.getTheOne(Order.class);
        OrderItem persistedOrderItem = persistedOrder.getOrderItems().stream().findFirst().orElseThrow();

        assertThat(persistedOrderItem).isEqualTo(item);
        assertThat(persistedOrderItem.getProduct()).isEqualTo(product);
        assertThat(persistedOrderItem.getItemName()).isEqualTo(product.getName());
        assertThat(persistedOrderItem.getDescription()).isEqualTo(product.getDescription());
    }

    @Test
    void test_update_product_details(){

        //----Arrange
        Product productToUpdate = doTransaction(em -> {
            TestDataCreator.createAddresses();

            Product product = TestDataCreator.createProduct(em, "orez", "pentru fiert", "src/image4", 12f, category1, seller1);

            Buyer buyerMerged = em.merge(buyer1);
            Seller sellerMerged = em.merge(seller1);

            Order order = new Order(address1, buyerMerged, sellerMerged, buyer1.getAccount().getTelephone());

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
        Order persistedOrder = entityFinder.getTheOne(Order.class);
        OrderItem persistedOrderItem = persistedOrder.getOrderItems().stream().findFirst().orElseThrow();
        Product persistedProduct = persistedOrderItem.getProduct();

        assertThat(persistedOrderItem.getItemPrice()).isEqualTo(12f);
        assertThat(persistedProduct.getPrice()).isEqualTo(50f);
    }

    @Test
    void test_remove_order(){

        //----Arrange
        Order addedOrder = doTransaction(em -> {
            TestDataCreator.createAddresses();

            Product product = TestDataCreator.createProduct(em, "orez", "pentru fiert", "src/image4", 12f, category1, seller1);

            Buyer buyerMerged = em.merge(buyer1);
            Seller sellerMerged = em.merge(seller1);

            Order order = new Order(address1, buyerMerged, sellerMerged, buyer1.getAccount().getTelephone());

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

    @Test
    void test_submit_order_if_order_not_draft(){

        //----Arrange
        Order addedOrder = doTransaction(em -> {

            TestDataCreator.createAddresses();
            Product product = TestDataCreator.createProduct(em, "orez", "pentru fiert", "src/image4", 12f, category1, seller1);

            Buyer buyerMerged = em.merge(buyer1);
            Seller sellerMerged = em.merge(seller1);

            Order order = new Order(address1, buyerMerged, sellerMerged, buyer1.getAccount().getTelephone());
            order.addProduct(product, 2f);
            em.persist(order);

            return order;
        });

        //----Act
        Exception exception = doTransaction(em -> {

            Order orderMerged = em.merge(addedOrder);

            orderMerged.submit();
            orderMerged.markedAsShipped();

            return assertThrows(IllegalOrderState.class, orderMerged::submit);
        });

        //----Assert
        assertTrue(exception.getMessage().contains("order state can only be draft if you want to submit"));
    }

    @Test
    void test_submit_order_if_not_added_item(){

        //----Arrange
        Order addedOrder = doTransaction(em -> {

            TestDataCreator.createAddresses();

            Buyer buyerMerged = em.merge(buyer1);
            Seller sellerMerged = em.merge(seller1);

            Order order = new Order(address1, buyerMerged, sellerMerged, buyer1.getAccount().getTelephone());

            em.persist(order);

            return order;
        });

        //----Act
        Exception exception = doTransaction(em -> {

            Order orderMerged = em.merge(addedOrder);

            return assertThrows(IllegalOrderState.class, orderMerged::submit);
        });

        //----Assert
        assertTrue(exception.getMessage().contains("order doesn't have any items, please add items to submit"));
    }

    @Test
    void test_check_submit_order(){

        //----Arrange
        Order addedOrder = doTransaction(em -> {

            TestDataCreator.createAddresses();
            Product product = TestDataCreator.createProduct(em, "orez", "pentru fiert", "src/image4", 12f, category1, seller1);

            Buyer buyerMerged = em.merge(buyer1);
            Seller sellerMerged = em.merge(seller1);

            Order order = new Order(address1, buyerMerged, sellerMerged, buyer1.getAccount().getTelephone());
            order.addProduct(product, 2f);
            em.persist(order);

            return order;
        });

        //----Act
        doTransaction(em -> {
            Order orderMerged = em.merge(addedOrder);
            orderMerged.submit();
        });

        //----Assert
        Order persistedOrder = entityFinder.getTheOne(Order.class);

        assertThat(persistedOrder.getOrderStatus()).isEqualTo(OrderStatus.SUBMITTED);
    }

    @Test
    void test_marked_shipped_if_order_not_submitted(){

        //----Arrange
        Order addedOrder = doTransaction(em -> {

            TestDataCreator.createAddresses();
            Product product = TestDataCreator.createProduct(em, "orez", "pentru fiert", "src/image4", 12f, category1, seller1);

            Buyer buyerMerged = em.merge(buyer1);
            Seller sellerMerged = em.merge(seller1);

            Order order = new Order(address1, buyerMerged, sellerMerged, buyer1.getAccount().getTelephone());
            order.addProduct(product, 2f);
            em.persist(order);

            return order;
        });

        //----Act
        Exception exception = doTransaction(em -> {

            Order orderMerged = em.merge(addedOrder);

            return assertThrows(IllegalOrderState.class, orderMerged::markedAsShipped);
        });

        //----Assert
        assertTrue(exception.getMessage().contains("order state can only be submitted if you want to ship"));
    }

    @Test
    void test_check_shipped_order(){

        //----Arrange
        Order addedOrder = doTransaction(em -> {

            TestDataCreator.createAddresses();
            Product product = TestDataCreator.createProduct(em, "orez", "pentru fiert", "src/image4", 12f, category1, seller1);

            Buyer buyerMerged = em.merge(buyer1);
            Seller sellerMerged = em.merge(seller1);

            Order order = new Order(address1, buyerMerged, sellerMerged, buyer1.getAccount().getTelephone());
            order.addProduct(product, 2f);
            em.persist(order);

            return order;
        });

        //----Act
        doTransaction(em -> {
            Order orderMerged = em.merge(addedOrder);
            orderMerged.submit();
            orderMerged.markedAsShipped();
        });

        //----Assert
        Order persistedOrder = entityFinder.getTheOne(Order.class);

        assertThat(persistedOrder.getOrderStatus()).isEqualTo(OrderStatus.SHIPPED);
    }

    @Test
    void test_marked_delivered_if_order_not_submitted(){

        //----Arrange
        Order addedOrder = doTransaction(em -> {

            TestDataCreator.createAddresses();
            Product product = TestDataCreator.createProduct(em, "orez", "pentru fiert", "src/image4", 12f, category1, seller1);

            Buyer buyerMerged = em.merge(buyer1);
            Seller sellerMerged = em.merge(seller1);

            Order order = new Order(address1, buyerMerged, sellerMerged, buyer1.getAccount().getTelephone());
            order.addProduct(product, 2f);
            em.persist(order);

            return order;
        });

        //----Act
        Exception exception = doTransaction(em -> {

            Order orderMerged = em.merge(addedOrder);

            return assertThrows(IllegalOrderState.class, orderMerged::markedAsDelivered);
        });

        //----Assert
        assertTrue(exception.getMessage().contains("order state can only be shipped if you want to deliver"));
    }

    @Test
    void test_marked_delivered_if_order_not_shipped(){

        //----Arrange
        Order addedOrder = doTransaction(em -> {

            TestDataCreator.createAddresses();
            Product product = TestDataCreator.createProduct(em, "orez", "pentru fiert", "src/image4", 12f, category1, seller1);

            Buyer buyerMerged = em.merge(buyer1);
            Seller sellerMerged = em.merge(seller1);

            Order order = new Order(address1, buyerMerged, sellerMerged, buyer1.getAccount().getTelephone());
            order.addProduct(product, 2f);
            em.persist(order);

            return order;
        });

        //----Act
        Exception exception = doTransaction(em -> {

            Order orderMerged = em.merge(addedOrder);
            orderMerged.submit();

            return assertThrows(IllegalOrderState.class, orderMerged::markedAsDelivered);
        });

        //----Assert
        assertTrue(exception.getMessage().contains("order state can only be shipped if you want to deliver"));
    }

    @Test
    void test_check_delivered_order(){

        //----Arrange
        Order addedOrder = doTransaction(em -> {

            TestDataCreator.createAddresses();
            Product product = TestDataCreator.createProduct(em, "orez", "pentru fiert", "src/image4", 12f, category1, seller1);

            Buyer buyerMerged = em.merge(buyer1);
            Seller sellerMerged = em.merge(seller1);

            Order order = new Order(address1, buyerMerged, sellerMerged, buyer1.getAccount().getTelephone());
            order.addProduct(product, 2f);
            em.persist(order);

            return order;
        });

        //----Act
        doTransaction(em -> {
            Order orderMerged = em.merge(addedOrder);
            orderMerged.submit();
            orderMerged.markedAsShipped();
            orderMerged.markedAsDelivered();
        });

        //----Assert
        Order persistedOrder = entityFinder.getTheOne(Order.class);

        assertThat(persistedOrder.getOrderStatus()).isEqualTo(OrderStatus.DELIVERED);
    }

    @Test
    void test_add_order_item_if_order_submitted(){

        //----Arrange
        Order addedOrder = doTransaction(em -> {
            TestDataCreator.createAddresses();

            Buyer buyerMerged = em.merge(buyer1);
            Seller sellerMerged = em.merge(seller1);
            Category categoryMerged = em.merge(category1);

            Product product = TestDataCreator.createProduct(em, "orez",
                    "pentru fiert", "src/image4", 12f, categoryMerged, sellerMerged);

            Order order = new Order(address1, buyerMerged, sellerMerged, buyer1.getAccount().getTelephone());
            order.addProduct(product, 2f);
            em.persist(order);

            return order;
        });

        //----Act
        Exception exception = doTransaction(em -> {

            Order orderMerged = em.merge(addedOrder);
            orderMerged.submit();

            Seller sellerMerged = em.merge(seller1);
            Category categoryMerged = em.merge(category1);

            Product product = TestDataCreator.createProduct(em, "grau", "pentru paine",
                    "src/image20", 8f, categoryMerged, sellerMerged);

            return assertThrows(IllegalOrderState.class, () -> orderMerged.addProduct(product, 2f));
        });

        //----Assert
        assertTrue(exception.getMessage().contains("can't add item, order already processed"));
    }

    @Test
    void test_add_order_item_if_item_belongs_to_other_seller(){

        //----Arrange
        Order addedOrder = doTransaction(em -> {
            TestDataCreator.createAddresses();

            Buyer buyerMerged = em.merge(buyer1);
            Seller sellerMerged = em.merge(seller1);
            Category categoryMerged = em.merge(category1);

            Product product = TestDataCreator.createProduct(em, "orez",
                    "pentru fiert", "src/image4", 12f, categoryMerged, sellerMerged);

            Order order = new Order(address1, buyerMerged, sellerMerged, buyer1.getAccount().getTelephone());
            order.addProduct(product, 2f);
            em.persist(order);

            return order;
        });

        //----Act
        Exception exception = doTransaction(em -> {

            Order orderMerged = em.merge(addedOrder);
            orderMerged.submit();

            Seller sellerMerged = em.merge(seller2);//added other seller and creates a new product
            Category categoryMerged = em.merge(category1);

            Product product = TestDataCreator.createProduct(em, "grau", "pentru paine",
                    "src/image20", 8f, categoryMerged, sellerMerged);

            return assertThrows(IllegalItemException.class, () -> orderMerged.addProduct(product, 2f));
        });

        //----Assert
        assertTrue(exception.getMessage().contains("can't add this item, it belongs to different seller"));
    }

    @Test
    void test_add_order_item_if_item_already_added(){

        //----Arrange
        Product addedProduct = doTransaction(em -> {
            TestDataCreator.createAddresses();

            Buyer buyerMerged = em.merge(buyer1);
            Seller sellerMerged = em.merge(seller1);

            Product product = TestDataCreator.createProduct(em, "orez", "pentru fiert", "src/image4", 12f, category1, sellerMerged);

            Order order = new Order(address1, buyerMerged, sellerMerged, buyer1.getAccount().getTelephone());
            order.addProduct(product, 2f);
            em.persist(order);

            return product;
        });

        //----Act
        Exception exception = doTransaction(em -> {

            Order orderMerged = entityFinder.getTheOne(Order.class);

            return assertThrows(IllegalItemException.class, () -> orderMerged.addProduct(addedProduct, 1f));
        });

        //----Assert
        assertTrue(exception.getMessage().contains("can't add this item, already added"));
    }

}
