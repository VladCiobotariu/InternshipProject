package com.ozius.internship.project.entity.cart;

import com.ozius.internship.project.entity.BaseEntity;
import com.ozius.internship.project.entity.Buyer;
import com.ozius.internship.project.entity.Product;
import com.ozius.internship.project.entity.seller.Seller;
import jakarta.persistence.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = Cart.TABLE_NAME)
public class Cart extends BaseEntity {
    public static final String TABLE_NAME = "cart";

    interface Columns {
        String BUYER_ID = "BUYER_ID";
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Cart.Columns.BUYER_ID, foreignKey = @ForeignKey(foreignKeyDefinition = "FOREIGN KEY (" + Columns.BUYER_ID + ") REFERENCES " + Seller.TABLE_NAME + " (" + BaseEntity.ID + ")  ON DELETE CASCADE"))
    private Buyer buyer;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = CartItem.Columns.CART_ID)
    private Set<CartItem> cartItems;

    public Cart() {
        this.cartItems = new HashSet<>();
    }

    public Cart(Buyer buyer) {
        this.buyer = buyer;
        this.cartItems = new HashSet<>();
    }

    public Set<CartItem> getCartItems() {
        return Collections.unmodifiableSet(cartItems);
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public float calculateItemPrice(CartItem cartItem) {
        return cartItem.getQuantity() * cartItem.getProduct().getPrice();
    }

    public double calculateTotalPrice() {
        return cartItems.stream()
                .mapToDouble(cartItem -> this.calculateItemPrice(cartItem))
                .sum();
    }

    private CartItem getCartItemByProduct(Product product) {
        return cartItems.stream()
                .filter(cartItem -> cartItem.getProduct().equals(product))
                .findFirst()
                .orElse(null);
    }

    // return CartItem because of TestDataCreator
    public CartItem addToCart(Product product, float quantity) {

        CartItem existingCartItem = getCartItemByProduct(product);

        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
            return existingCartItem;
        } else {
            CartItem cartItem = new CartItem(quantity, product);
            this.cartItems.add(cartItem);
            return cartItem;
        }
    }

    public void removeFromCart(Product product) {
        CartItem cartItem = getCartItemByProduct(product);

        if(cartItem != null) {
            this.cartItems.remove(cartItem);
            System.out.println("CartItem removed: " + cartItem);
        }
    }

    public void updateCartItem(Product product, float quantity) {
        CartItem cartItem = getCartItemByProduct(product);

        if (cartItem != null) {
            cartItem.setQuantity(quantity);
        }
    }

    public void assignBuyerToCart(Buyer buyer) {
        this.buyer = buyer;
    }

    @Override
    public String toString() {
        return "Cart{" +
                ", cartItems=" + cartItems +
                '}';
    }
}
