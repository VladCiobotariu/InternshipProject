package com.ozius.internship.project.entity;

import com.ozius.internship.project.TestDataCreatorErika;
import com.ozius.internship.project.entity.cart.Cart;
import com.ozius.internship.project.entity.cart.CartItem;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CartEntityTest extends EntityBaseTest {

    @Override
    public void createTestData(EntityManager em) {
        TestDataCreatorErika.createBaseDataForProduct(em);
    }

    @Test
    public void cart_is_created() {
        // ----Act
        doTransaction(em -> {
            //TODO - should not possible to have card entity without a buyer attached.
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

        // TODO how would you optimise/cleanup the following statements?
        Cart assertedCart = carts.isEmpty() ? null : carts.get(0);
        assertThat(assertedCart).isNull();
        assertThat(entityFinder.findAll(Cart.class)).isEmpty();

        //TODO valid assert but no card items have been added in arrange.
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
            Cart managedCart = em.merge(cart); // managedCart for em //TODO is this needed? hint: Please pay attention to EntityManager used by EntityFinder
            managedCart.addToCart(em.merge(TestDataCreatorErika.Products.product1), 2);
            managedCart.addToCart(em.merge(TestDataCreatorErika.Products.product2), 3);
        });

        // ----Assert
        Cart persistedCart = entityFinder.getTheOne(Cart.class);
        assertThat(persistedCart.calculateTotalPrice()).isEqualTo(14);
        assertThat(persistedCart.getCartItems()).hasSize(2);

        Iterator<CartItem> iter = persistedCart.getCartItems().iterator();
        CartItem cartItem1 = iter.next();
        CartItem cartItem2 = iter.next();
        //TODO this assert will never fail.
        assertThat(persistedCart.getCartItems())
                .extracting(BaseEntity::getId).containsExactlyInAnyOrder(cartItem1.getId(), cartItem2.getId());

        //TODO CartItem.quantity and CartItem.product are not asserted. (hint: do we need to assert them in this test, how to simplify things?)
    }

    @Test
    public void cartItem_is_updated() {
        // ----Arrange
        doTransaction(em -> {
            Cart cart = new Cart();
            //TODO for readability it's probably better to create a new product here using #createProduct(...)
            // In general, the test data that is directly influencing the asserts should be explicitly created
            // The problem here could be that someone else might modify the product price and it might this test to break.
            Product pr = em.merge(TestDataCreatorErika.Products.product1);
            cart.addToCart(pr, 2);
            em.persist(cart);

        });

        // ----Act
        // need to get the object again because cart is now detached
        doTransaction(em -> {
            EntityFinder entityFinder = new EntityFinder(em);
            Cart cart = entityFinder.getTheOne(Cart.class);
            Cart cartToModify = em.merge(cart); //TODO is this needed?
            CartItem cartItem = cartToModify.getCartItems().iterator().next();
            cartToModify.updateCartItem(cartItem, 20);
        });

        // ----Assert
        Cart persistedCart = entityFinder.getTheOne(Cart.class);
        assertThat(persistedCart.calculateTotalPrice()).isEqualTo(50);

        CartItem persistedCartItem = persistedCart.getCartItems().iterator().next();
        // TODO no need for .getId() due to equals and hashcode impl in BaseEntity
        assertThat(persistedCartItem.getProduct().getId()).isEqualTo(TestDataCreatorErika.Products.product1.getId());
        assertThat(persistedCartItem.getQuantity()).isEqualTo(20);

    }

    @Test
    public void cartItem_is_deleted() {
        // ----Arrange
        doTransaction(em -> {
            Cart cart = new Cart();
            em.persist(cart);
            Product pr = em.merge(TestDataCreatorErika.Products.product1);
            cart.addToCart(pr, 2); // rosii de pret 2.5, cantitate 2
        });

        // ----
        doTransaction(em -> {
            EntityFinder entityFinder = new EntityFinder(em);
            Cart cart = entityFinder.getTheOne(Cart.class);
            Cart cartToRemoveFrom = em.merge(cart); //TODO is this needed?
            CartItem cartItem = cartToRemoveFrom.getCartItems().stream().findFirst().orElse(null);
            cartToRemoveFrom.removeFromCart(cartItem.getProduct());
        });

        // ----Assert
        Cart persistedCart = entityFinder.getTheOne(Cart.class);
        Set<CartItem> persistedCartItem = persistedCart.getCartItems();

        // TODO both assert the same thing, only one is enough. isEmpty() is preferred to assert that collection is empty.
        assertThat(persistedCartItem).isEmpty();
        assertThat(persistedCart.getCartItems()).hasSize(0);
    }

}
