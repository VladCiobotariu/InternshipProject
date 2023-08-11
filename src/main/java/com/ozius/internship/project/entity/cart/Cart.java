package com.ozius.internship.project.entity.cart;

import com.ozius.internship.project.entity.BaseEntity;
import com.ozius.internship.project.entity.Buyer;
import com.ozius.internship.project.entity.Product;
import com.ozius.internship.project.entity.exeption.IllegalQuantityException;
import com.ozius.internship.project.entity.exeption.InvalidCartItemQuantity;
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
        String TOTAL_PRICE = "TOTAL_PRICE";
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Cart.Columns.BUYER_ID, foreignKey = @ForeignKey(foreignKeyDefinition = "FOREIGN KEY (" + Columns.BUYER_ID + ") REFERENCES " + Seller.TABLE_NAME + " (" + BaseEntity.ID + ")  ON DELETE CASCADE"))
    private Buyer buyer;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = CartItem.Columns.CART_ID)
    private Set<CartItem> cartItems;

    @Column(name = Columns.TOTAL_PRICE, nullable = false)
    private double totalCartPrice;

    public Cart() {
        this.cartItems = new HashSet<>();
    }

    public Cart(Buyer buyer) {
        this.buyer = buyer;
        this.cartItems = new HashSet<>();
        this.totalCartPrice = 0f;
    }

    public Set<CartItem> getCartItems() {
        return Collections.unmodifiableSet(cartItems);
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public double getTotalCartPrice() {
        return totalCartPrice;
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

    public CartItem addToCart(Product product, float quantity) {

        CartItem existingCartItem = getCartItemByProduct(product);

        if(quantity == 0) {
            throw new IllegalQuantityException("Quantity cannot be 0!");
        }
        if(quantity < 0) {
            throw new InvalidCartItemQuantity("Quantity can be less than 0!");
        }

        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
            this.totalCartPrice = calculateTotalPrice();
            return existingCartItem;
        } else {
            CartItem cartItem = new CartItem(quantity, product);
            this.cartItems.add(cartItem);
            this.totalCartPrice = calculateTotalPrice();
            return cartItem;
        }
    }

    // TODO - should i check the quantity to make quantity-1 or just delete the entire cartItem?
    public void removeFromCart(Product product) {
        CartItem cartItem = getCartItemByProduct(product);

        if(cartItem != null) {
            this.cartItems.remove(cartItem);
            this.totalCartPrice = calculateTotalPrice();
        }
    }

    public void updateCartItem(Product product, float quantity) {
        if(quantity == 0) {
            throw new IllegalQuantityException("Quantity cannot be 0!");
        }
        if(quantity < 0) {
            throw new InvalidCartItemQuantity("Quantity can be less than 0!");
        }

        CartItem cartItem = getCartItemByProduct(product);

        if (cartItem != null) {
            cartItem.setQuantity(quantity);
            this.totalCartPrice = calculateTotalPrice();
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
