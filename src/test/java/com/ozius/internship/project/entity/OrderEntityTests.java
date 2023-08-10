package com.ozius.internship.project.entity;

import com.ozius.internship.project.TestDataCreator;
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
import static com.ozius.internship.project.TestDataCreator.Products.product2;
import static com.ozius.internship.project.TestDataCreator.Sellers.seller1;
import static org.assertj.core.api.Assertions.*;

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
            //TODO is it okay to add a address to a static field and use it?
            // so i dont have to create an address everytime but the address isn't added to the database
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
        Product addedProduct = doTransaction(em -> {
            Order orderMerged = em.merge(addedOrder);
            orderMerged.submit();

            Seller sellerMerged = em.merge(seller1);
            Category categoryMerged = em.merge(category1);

            Product product = TestDataCreator.createProduct(em, "grau", "pentru paine",
                    "src/image20", 8f, categoryMerged, sellerMerged);

            try {
                orderMerged.addProduct(product, 2f);
                fail("supposed to fail");
            }catch (IllegalOrderState exception){
                //nothing to do, supposed to fail
            }

            return product;
        });

        //----Assert
        //method should end with throwable exception
    }
}
