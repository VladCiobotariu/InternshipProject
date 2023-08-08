package com.ozius.internship.project.entity.cart;

import com.ozius.internship.project.entity.BaseEntity;
import com.ozius.internship.project.entity.Product;
import jakarta.persistence.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = Cart.TABLE_NAME)
public class Cart extends BaseEntity {
    public static final String TABLE_NAME = "cart";

    interface Columns { //TODO remove if no columns
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = CartItem.Columns.CART_ID)
    private Set<CartItem> cartItems = new HashSet<>(); // TODO only initialize entity fields in constructor. Better readability


    //TODO cleanup
//    public Cart() {
//    }

    public Cart() {
        this.cartItems = new HashSet<>();
    }

    public Set<CartItem> getCartItems() {
        return Collections.unmodifiableSet(cartItems);
    }

    public float calculateItemPrice(CartItem cartItem) {
        return cartItem.getQuantity() * cartItem.getProduct().getPrice();
    }

    public double calculateTotalPrice() {
        return cartItems.stream()
                .mapToDouble(cartItem -> this.calculateItemPrice(cartItem))
                .sum();
    }

    //TODO use Product in interface method, same as removeFromCart and addToCard. Find a better self-explanatory name
    public void modifyItem(CartItem cartItem, float quantity) {
        cartItem.setQuantity(quantity);
    }

    private CartItem getCartItemByProduct(Product product) {
        return cartItems.stream()
                .filter(cartItem -> cartItem.getProduct().equals(product))
                .findFirst()
                .orElse(null);
    }

    // todo it's probably not required to return the CartItem
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
    // todo it's probably not required to return the CartItem
    public CartItem removeFromCart(Product product) {
        //TODO there is already a method which could be used for search see getCartItemByProduct
        CartItem cartItem = this.cartItems.stream().filter(ci -> ci.getProduct().getId() == product.getId()).findFirst().orElse(null);
        this.cartItems.remove(cartItem);
        return cartItem;
    }

    //todo toString() missing usually useful for debug purpose.

}
