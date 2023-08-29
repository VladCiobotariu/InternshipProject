package com.ozius.internship.project.entity;

import com.ozius.internship.project.entity.buyer.Buyer;
import com.ozius.internship.project.entity.cart.Cart;
import com.ozius.internship.project.entity.cart.CartItem;
import com.ozius.internship.project.entity.exception.IllegalQuantityException;
import com.ozius.internship.project.entity.exception.NotFoundException;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.ozius.internship.project.TestDataCreator.*;
import static com.ozius.internship.project.TestDataCreator.Categories.category1;
import static com.ozius.internship.project.TestDataCreator.Categories.category2;
import static com.ozius.internship.project.TestDataCreator.Products.*;
import static com.ozius.internship.project.TestDataCreator.Sellers.seller1;
import static com.ozius.internship.project.TestDataCreator.Sellers.seller2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertThat(persistedCart.getTotalCartPrice()).isEqualTo(0);
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
            Product p1 = createProduct(em, "cartofi", "pentru fiert", "src/image4", 12.7f, category1, seller1);
            Product p2 = createProduct(em, "pere", "pentru glucoza", "src/image77", 5f, category2, seller2);
            cart.addOrUpdateItem(p1, 3);
            cart.addOrUpdateItem(p2, 2);
        });

        // ----Assert
        Cart persistedCart = entityFinder.getTheOne(Cart.class);
        assertThat(persistedCart.getTotalCartPrice()).isEqualTo(48.1f);
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
            Product p1 = createProduct(em, "pere", "pentru glucoza", "src/image77", 5f, category2, seller2);
            cart.addOrUpdateItem(p1, 2);
        });

        // ----Assert
        Cart persistedCart = entityFinder.getTheOne(Cart.class);
        CartItem cartItem = persistedCart.getCartItems().stream().findFirst().orElseThrow();

        assertThat(cartItem.getProduct().getName()).isEqualTo("pere");
        assertThat(cartItem.getQuantity()).isEqualTo(2);
    }

    @Test
    public void cart_is_deleted() {
        // ----Arrange
        doTransaction(em -> {
            Cart cart = new Cart();
            Product pr = em.merge(product1);
            cart.addOrUpdateItem(pr, 1);
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
    public void same_cart_item_added() {
        // ----Arrange
        doTransaction(em -> {
            Cart cart = new Cart();
            cart.addOrUpdateItem(em.merge(product1), 2);
            em.persist(cart);

        });

        // ----Act
        doTransaction(em -> {
            EntityFinder entityFinder = new EntityFinder(em);
            Cart cart = entityFinder.getTheOne(Cart.class);
            cart.addOrUpdateItem(em.merge(product1), 1);
        });

        // ----Assert
        Cart persistedCart = entityFinder.getTheOne(Cart.class);
        CartItem cartItem = persistedCart.getCartItems().stream().findFirst().orElseThrow();

        assertThat(cartItem.getProduct()).isEqualTo(product1);
        assertThat(cartItem.getQuantity()).isEqualTo(3);
    }

    @Test
    public void cartItem_is_updated() {
        // ----Arrange
        Product productSaved = doTransaction(em -> {
            Cart cart = new Cart();
            Product product = createProduct(em, "popcorn", "descriere popcorn", "/popcorn", 5F, category1, seller1);
            cart.addOrUpdateItem(product, 2);
            em.persist(cart);

            return product;
        });

        // ----Act
        doTransaction(em -> {
            EntityFinder entityFinder = new EntityFinder(em);
            Cart cart = entityFinder.getTheOne(Cart.class);
            cart.addOrUpdateItem(productSaved, 20); // 20 + 2
        });

        // ----Assert
        Cart persistedCart = entityFinder.getTheOne(Cart.class);
        CartItem cartItem = persistedCart.getCartItems().stream().findFirst().orElseThrow();
        Product persistedProduct = cartItem.getProduct();

        assertThat(persistedProduct).isEqualTo(productSaved);
        assertThat(persistedCart.getTotalCartPrice()).isEqualTo(110);
        assertThat(cartItem.getProduct()).isEqualTo(productSaved);
        assertThat(cartItem.getQuantity()).isEqualTo(22);

    }

    @Test
    public void cartItem_is_deleted() {
        // ----Arrange
        Product pr = doTransaction(em -> {
            Cart cart = new Cart();
            Product product = em.merge(product1);
            cart.addOrUpdateItem(product, 1);
            em.persist(cart);

            return product;
        });

        // ----Act
        doTransaction(em -> {
            EntityFinder entityFinder = new EntityFinder(em);
            Cart cart = entityFinder.getTheOne(Cart.class);
            cart.removeFromCart(pr);
        });

        // ----Assert
        Cart persistedCart = entityFinder.getTheOne(Cart.class);
        Set<CartItem> persistedCartItems = persistedCart.getCartItems();

        assertThat(persistedCartItems).isEmpty();
        assertThat(persistedCart.getTotalCartPrice()).isEqualTo(0);
    }

    @Test
    public void buyer_added_to_cart() {
        // ----Arrange
        doTransaction(em -> {
            Cart cart = new Cart();
            em.persist(cart);
        });

        // ----Act
        Buyer savedBuyer = doTransaction(em -> {
            EntityFinder entityFinder = new EntityFinder(em);
            Cart cart = entityFinder.getTheOne(Cart.class);
            Buyer buyer = createBuyer(em, new UserAccount("Marcel", "Danila", "marceldanila@gmail.com","/src/image90","0777777635"));
            cart.assignBuyerToCart(buyer);

            return buyer;
        });

        // ----Assert
        Cart persistedCart = entityFinder.getTheOne(Cart.class);
        assertThat(persistedCart.getBuyer()).isEqualTo(savedBuyer);
    }

    @Test
    public void cart_is_cleared() {
        //----Arrange
        doTransaction(em -> {
            Cart cart = new Cart();
            cart.addOrUpdateItem(em.merge(product1), 1);
            cart.addOrUpdateItem(em.merge(product2), 2);
            em.persist(cart);
        });

        //----Act
        doTransaction(em -> {
            EntityFinder entityFinder = new EntityFinder(em);
            Cart cart = entityFinder.getTheOne(Cart.class);
            cart.clearCartFromAllCartItems();
        });

        //----Assert
        Cart persistedCart = entityFinder.getTheOne(Cart.class);

        assertThat(persistedCart.getCartItems()).isEmpty();
        assertThat(persistedCart.getTotalCartPrice()).isEqualTo(0);
        assertThat(persistedCart.getCartItems().size()).isEqualTo(0);
    }

    @Test
    public void adding_cartItem_with_zero_quantity_throws_exception() {
        // ----Arrange
        doTransaction(em -> {
            Cart cart = new Cart();
            em.persist(cart);
        });

        // ----Act
        Exception exception = doTransaction(em -> {
            EntityFinder entityFinder = new EntityFinder(em);
            Cart cart = entityFinder.getTheOne(Cart.class);
            Product p1 = createProduct(em, "cartofi", "pentru fiert", "src/image4", 12.7f, category1, seller1);

            return assertThrows(IllegalQuantityException.class, () -> cart.addOrUpdateItem(p1, 0));
        });

        //----Assert
        assertTrue(exception.getMessage().contains("Quantity cannot be 0!"));
    }

    @Test
    public void updating_cartItem_with_zero_quantity_throws_exception() {
        // ----Arrange
        Product productSaved = doTransaction(em -> {
            Cart cart = new Cart();
            Product product = createProduct(em, "popcorn", "descriere popcorn", "/popcorn", 5F, category1, seller1);
            cart.addOrUpdateItem(product, 2);
            em.persist(cart);

            return product;
        });

        // ----Act
        Exception exception = doTransaction(em -> {
            EntityFinder entityFinder = new EntityFinder(em);
            Cart cart = entityFinder.getTheOne(Cart.class);
            return assertThrows(IllegalQuantityException.class, () -> cart.addOrUpdateItem(productSaved, 0));
        });

        // ----Assert
        assertTrue(exception.getMessage().contains("Quantity cannot be 0!"));
    }

    @Test
    public void adding_cartItem_with_quantity_less_than_zero_throws_exception() {
        // ----Arrange
        doTransaction(em -> {
            Cart cart = new Cart();
            em.persist(cart);
        });

        // ----Act
        Exception exception = doTransaction(em -> {
            EntityFinder entityFinder = new EntityFinder(em);
            Cart cart = entityFinder.getTheOne(Cart.class);
            Product p1 = createProduct(em, "cartofi", "pentru fiert", "src/image4", 12.7f, category1, seller1);
            return assertThrows(IllegalQuantityException.class, () -> cart.addOrUpdateItem(p1, -5));
        });

        // ----Assert
        assertTrue(exception.getMessage().contains("Quantity cannot be less than 0!"));

    }

    @Test
    public void updating_cartItem_with_quantity_less_than_zero_throws_exception() {
        // ----Arrange
        Product productSaved = doTransaction(em -> {
            Cart cart = new Cart();
            Product product = createProduct(em, "popcorn", "descriere popcorn", "/popcorn", 5F, category1, seller1);
            cart.addOrUpdateItem(product, 2);
            em.persist(cart);

            return product;
        });

        // ----Act
        Exception exception = doTransaction(em -> {
            EntityFinder entityFinder = new EntityFinder(em);
            Cart cart = entityFinder.getTheOne(Cart.class);
            return assertThrows(IllegalQuantityException.class, () -> cart.addOrUpdateItem(productSaved, -2));
        });

        // ----Assert
        assertTrue(exception.getMessage().contains("Quantity cannot be less than 0!"));
    }

    @Test
    public void cartItem_not_found_when_want_to_remove() {
        // ----Arrange
        doTransaction(em -> {
            Cart cart = new Cart();
            cart.addOrUpdateItem(em.merge(product1), 1);
            em.persist(cart);

        });

        // ----Act
        Exception exception = doTransaction(em -> {
            EntityFinder entityFinder = new EntityFinder(em);
            Cart cart = entityFinder.getTheOne(Cart.class);
            return assertThrows(NotFoundException.class, () -> cart.removeFromCart(product2));
        });

        // ----Assert
        assertTrue(exception.getMessage().contains("Cart item was not found in the list!"));
    }

}