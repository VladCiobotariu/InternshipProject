package com.ozius.internship.project.entity.cart;

import com.ozius.internship.project.entity.BaseEntity;
import com.ozius.internship.project.entity.buyer.Buyer;
import com.ozius.internship.project.entity.Product;
import com.ozius.internship.project.entity.exception.IllegalQuantityException;
import com.ozius.internship.project.entity.exception.NotFoundException;
import com.ozius.internship.project.entity.seller.Seller;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    @JoinColumn(name = Cart.Columns.BUYER_ID, foreignKey = @ForeignKey(foreignKeyDefinition = "FOREIGN KEY (" + Columns.BUYER_ID + ") REFERENCES " + Buyer.TABLE_NAME + " (" + BaseEntity.ID + ")  ON DELETE CASCADE"))
    private Buyer buyer;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = CartItem.Columns.CART_ID, foreignKey = @ForeignKey(foreignKeyDefinition =
            "FOREIGN KEY (" + CartItem.Columns.CART_ID + ") REFERENCES " + Cart.TABLE_NAME + " (" + BaseEntity.ID + ")  ON DELETE CASCADE"))
    private Set<CartItem> cartItems;

    @Column(name = Columns.TOTAL_PRICE, nullable = false, scale = 2)
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

    //TODO modify Entities to big decimal instead of double
    public double calculateTotalPrice() {
        double sum = cartItems.stream()
                .mapToDouble(this::calculateItemPrice)
                .sum();
        return BigDecimal.valueOf(sum).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    private CartItem getCartItemByProduct(Product product) {
        return cartItems.stream()
                .filter(cartItem -> cartItem.getProduct().equals(product))
                .findFirst()
                .orElse(null);
    }

    public void addOrUpdateItem(Product product, float quantity) {

        CartItem existingCartItem = getCartItemByProduct(product);

        if(quantity == 0) {
            throw new IllegalQuantityException("Quantity cannot be 0!");
        }
        if(quantity < 0) {
            throw new IllegalQuantityException("Quantity cannot be less than 0!");
        }

        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
        } else {
            CartItem cartItem = new CartItem(quantity, product);
            this.cartItems.add(cartItem);
        }
        this.totalCartPrice = calculateTotalPrice();
    }

    public void removeFromCart(Product product) {
        CartItem cartItem = getCartItemByProduct(product);
        if(cartItem == null) {
            throw new NotFoundException("Cart item was not found in the list!");
        }
        this.cartItems.remove(cartItem);
        this.totalCartPrice = calculateTotalPrice();
    }

    public void clearCartFromAllCartItems() {
        this.cartItems.clear();
        this.totalCartPrice = 0f;
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
