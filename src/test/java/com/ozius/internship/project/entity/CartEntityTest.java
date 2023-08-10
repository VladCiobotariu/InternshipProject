package com.ozius.internship.project.entity;

import com.ozius.internship.project.entity.cart.Cart;
import com.ozius.internship.project.entity.cart.CartItem;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.Set;

import static com.ozius.internship.project.TestDataCreator.Buyers.buyer1;
import static com.ozius.internship.project.TestDataCreator.Categories.category1;
import static com.ozius.internship.project.TestDataCreator.Categories.category2;
import static com.ozius.internship.project.TestDataCreator.Products.*;
import static com.ozius.internship.project.TestDataCreator.Sellers.seller1;
import static com.ozius.internship.project.TestDataCreator.Sellers.seller2;
import static com.ozius.internship.project.TestDataCreator.createBaseDataForProduct;
import static com.ozius.internship.project.TestDataCreator.createProduct;
import static org.assertj.core.api.Assertions.assertThat;

public class CartEntityTest extends EntityBaseTest {

    @Override
    public void createTestData(EntityManager em) {
        createBaseDataForProduct(em);
    }

    @Test
    public void cart_is_created() {
        // ----Act
        doTransaction(em -> {
            Cart cart = new Cart();
            em.persist(cart);
        });

        // ----Assert
        Cart persistedCart = entityFinder.getTheOne(Cart.class);

        assertThat(persistedCart).isNotNull();
        assertThat(persistedCart.getCartItems()).isEmpty();
        assertThat(persistedCart.calculateTotalPrice()).isEqualTo(0);
    }

    @Test
    public void cartItem_is_added() {
        // ----Arrange
        doTransaction(em -> {
            Cart cart = new Cart();
            em.persist(cart);
        });

        // ----Act
        doTransaction(em -> {
            EntityFinder entityFinder = new EntityFinder(em);
            Cart cart = entityFinder.getTheOne(Cart.class);
            cart.addToCart(em.merge(product1), 2);
            cart.addToCart(em.merge(product3), 3);
        });

        // ----Assert
        Cart persistedCart = entityFinder.getTheOne(Cart.class);
        assertThat(persistedCart.calculateTotalPrice()).isEqualTo(48.1f);
        assertThat(persistedCart.getCartItems()).hasSize(2);
    }

    @Test
    public void cart_item_added_correctly() {
        // ----Arrange
        doTransaction(em -> {
            Cart cart = new Cart();
            em.persist(cart);
        });

        // ----Act
        doTransaction(em -> {
            EntityFinder entityFinder = new EntityFinder(em);
            Cart cart = entityFinder.getTheOne(Cart.class);
            Product p1 = createProduct(em, "orez", "pentru fiert", "src/image4", 12.7f, category1, seller1);
            Product p2 = createProduct(em, "mar", "pentru glucoza", "src/image77", 5f, category2, seller2);
            cart.addToCart(p1, 2);
            cart.addToCart(p2, 3);
        });

        // ----Assert
        Cart persistedCart = entityFinder.getTheOne(Cart.class);
        Iterator<CartItem> iter = persistedCart.getCartItems().iterator();
        CartItem cartItem1 = iter.next();
        CartItem cartItem2 = iter.next();

        assertThat(cartItem1.getProduct().getName()).isEqualTo("orez");
        assertThat(cartItem1.getQuantity()).isEqualTo(2);

        assertThat(cartItem2.getProduct().getName()).isEqualTo("mar");
        assertThat(cartItem2.getQuantity()).isEqualTo(3);
    }

    @Test
    public void cart_is_deleted() {
        // ----Arrange
        doTransaction(em -> {
            Cart cart = new Cart();
            cart.addToCart(product1, 1);
            em.persist(cart);
        });

        // ----Act
        doTransaction(em -> {
            EntityFinder entityFinder = new EntityFinder(em);
            Cart cart = entityFinder.getTheOne(Cart.class);
            Cart cartToRemove = em.merge(cart);
            em.remove(cartToRemove);
        });

        // ----Assert
        assertThat(entityFinder.findAll(Cart.class)).isEmpty();
        assertThat(entityFinder.findAll(CartItem.class)).isEmpty();
    }

    @Test
    public void cartItem_is_updated() {
        // ----Arrange
        doTransaction(em -> {
            Cart cart = new Cart();
            Product product = createProduct(em, "popcorn", "descriere popcorn", "/popcorn", 5F, category1, seller1);
            cart.addToCart(product, 2);
            em.persist(cart);
        });

        // ----Act
        // need to get the object again because cart is now detached
        doTransaction(em -> {
            EntityFinder entityFinder = new EntityFinder(em);
            Cart cart = entityFinder.getTheOne(Cart.class);
            Product product = entityFinder.getProductByName("popcorn");
            cart.updateCartItem(product, 20);
        });

        // ----Assert
        Cart persistedCart = entityFinder.getTheOne(Cart.class);
        Product product = entityFinder.getProductByName("popcorn");

        assertThat(persistedCart.calculateTotalPrice()).isEqualTo(100);

        CartItem persistedCartItem = persistedCart.getCartItems().iterator().next();
        assertThat(persistedCartItem.getProduct()).isEqualTo(product);
        assertThat(persistedCartItem.getQuantity()).isEqualTo(20);

    }

    @Test
    public void cartItem_is_deleted() {
        // ----Arrange
        doTransaction(em -> {
            Cart cart = new Cart();
            cart.addToCart(product1, 1);
            em.persist(cart);
        });

        // ----Act
        doTransaction(em -> {
            EntityFinder entityFinder = new EntityFinder(em);
            Cart cart = entityFinder.getTheOne(Cart.class);
            Product product = entityFinder.getProductByName(product1.getName());
            cart.removeFromCart(product);
        });

        // ----Assert
        Cart persistedCart = entityFinder.getTheOne(Cart.class);
        Set<CartItem> persistedCartItems = persistedCart.getCartItems();

        assertThat(persistedCartItems).isEmpty();
    }

    @Test
    public void buyer_added_to_cart() {
        // ----Arrange
        doTransaction(em -> {
            Cart cart = new Cart();
            em.persist(cart);
        });

        // ----Act
        doTransaction(em -> {
            EntityFinder entityFinder = new EntityFinder(em);
            Cart cart = entityFinder.getTheOne(Cart.class);
            cart.assignBuyerToCart(buyer1);
        });

        // ----Assert
        Cart persistedCart = entityFinder.getTheOne(Cart.class);
        assertThat(persistedCart.getBuyer()).isEqualTo(buyer1);
    }

}
