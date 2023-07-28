package com.ozius.internship.project.entity;

import com.ozius.internship.project.TestDataCreator;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CartEntityTest extends EntityBaseTest {

    @Override
    public void createTestData(EntityManager em) {
        TestDataCreator.createBaseDataForProduct(em);
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
    public void cart_is_deleted() {
        // ----Arrange
        doTransaction(em -> {
            Cart cart = new Cart();
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
        List<Cart> carts = entityFinder.findAll(Cart.class);
        Cart assertedCart = carts.isEmpty() ? null : carts.get(0);
        assertThat(assertedCart).isNull();
        assertThat(entityFinder.findAll(Cart.class)).isEmpty();
        assertThat(entityFinder.findAll(CartItem.class)).isEmpty();
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
            Cart cart = entityFinder.getTheOne(Cart.class); // cartRepository uses emb (cart is managed entity but for emb)
            Cart managedCart = em.merge(cart); // managedCart for em
            managedCart.addToCart(em.merge(TestDataCreator.Products.product1), 2);
            managedCart.addToCart(em.merge(TestDataCreator.Products.product2), 3);
        });

        // ----Assert
        Cart persistedCart = entityFinder.getTheOne(Cart.class);
        assertThat(persistedCart.calculateTotalPrice()).isEqualTo(14);
        assertThat(persistedCart.getCartItems()).hasSize(2);

        Iterator<CartItem> iter = persistedCart.getCartItems().iterator();
        CartItem cartItem1 = iter.next();
        CartItem cartItem2 = iter.next();
        assertThat(persistedCart.getCartItems())
                .extracting(BaseEntity::getId).containsExactlyInAnyOrder(cartItem1.getId(), cartItem2.getId());

    }

    @Test
    public void cartItem_is_updated() {
        // ----Arrange
        doTransaction(em -> {
            Cart cart = new Cart();
            Product pr = em.merge(TestDataCreator.Products.product1);
            cart.addToCart(pr, 2);
            em.persist(cart);

        });

        // ----Act
        // need to get the object again because cart is now detached
        doTransaction(em -> {
            EntityFinder entityFinder = new EntityFinder(em);
            Cart cart = entityFinder.getTheOne(Cart.class);
            Cart cartToModify = em.merge(cart);
            CartItem cartItem = cartToModify.getCartItems().iterator().next();
            cartToModify.modifyItem(cartItem, 20);
        });

        // ----Assert
        Cart persistedCart = entityFinder.getTheOne(Cart.class);
        assertThat(persistedCart.calculateTotalPrice()).isEqualTo(50);

        CartItem persistedCartItem = persistedCart.getCartItems().iterator().next();
        assertThat(persistedCartItem.getProduct().getId()).isEqualTo(TestDataCreator.Products.product1.getId());
        assertThat(persistedCartItem.getQuantity()).isEqualTo(20);

    }

    @Test
    public void cartItem_is_deleted() {
        // ----Arrange
        doTransaction(em -> {
            Cart cart = new Cart();
            em.persist(cart);
            Product pr = em.merge(TestDataCreator.Products.product1);
            cart.addToCart(pr, 2); // rosii de pret 2.5, cantitate 2
        });

        // ----
        doTransaction(em -> {
            EntityFinder entityFinder = new EntityFinder(em);
            Cart cart = entityFinder.getTheOne(Cart.class);
            Cart cartToRemoveFrom = em.merge(cart);
            CartItem cartItem = cartToRemoveFrom.getCartItems().stream().findFirst().orElse(null);
            cartToRemoveFrom.removeFromCart(cartItem.getProduct());
        });

        // ----Assert
        Cart persistedCart = entityFinder.getTheOne(Cart.class);
        Set<CartItem> persistedCartItem = persistedCart.getCartItems();

        assertThat(persistedCartItem).isEmpty();
        assertThat(persistedCart.getCartItems()).hasSize(0);
    }

}
