package com.ozius.internship.project.entity;

import com.ozius.internship.project.entity.cart.Cart;
import jakarta.persistence.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = Buyer.TABLE_NAME)
public class Buyer extends BaseEntity{

    public static final String TABLE_NAME = "BUYER_INFO"; //TODO rename table, remove _INFO
    public static final String JOIN_TABLE_NAME = "BUYER_FAVORITES";

    interface Columns{
        String ACCOUNT_ID = "ACCOUNT_ID";
        String CART_ID = "CART_ID";
        String BUYER_ID = "BUYER_ID";
        String PRODUCT_ID = "PRODUCT_ID";

    }

    //TODO - the current model couples the card to the buyer, do we have any reasons to do that(business logic reasons)
    // Also it's currently possible for a card to exist in the database even it's not assigned to any buyer (orphan card)
    // how would you fix that?
    // while doing the refactoring, please also ensure that a buyer can only one card(the current implementation ensures that but it has the other disadvantages listed above).
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = Columns.CART_ID, nullable = false)
    private Cart cart;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = Columns.ACCOUNT_ID, nullable = false)
    private UserAccount account;

    @ManyToMany(cascade = CascadeType.ALL) /*Cascade all for join table*/
    @JoinTable(
            name = JOIN_TABLE_NAME,
            joinColumns = @JoinColumn(name = Columns.BUYER_ID),
            inverseJoinColumns = @JoinColumn(name = Columns.PRODUCT_ID))
    private Set<Product> favoriteProducts;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = BuyerAddress.Columns.BUYER_ID, nullable = false)
    private Set<BuyerAddress> addresses;

    public Buyer() { //TODO please use protected for JPA constructors.
    }

    public Buyer(UserAccount account) {
        this.cart = new Cart();
        this.account = account;
        this.favoriteProducts = new HashSet<>();
        this.addresses = new HashSet<>();
    }

    public Cart getCart() {
        return cart;
    }

    public UserAccount getAccount() {
        return account;
    }

    public Set<Product> getFavoriteProducts() {
        return Collections.unmodifiableSet(favoriteProducts);
    }

    public Set<BuyerAddress> getAddresses() {
        return Collections.unmodifiableSet(addresses);
    }

    //TODO add tests.
    public void addFavorite(Product product){
        this.favoriteProducts.add(product);
    }

    //TODO add tests.
    public void removeFavorite(Product product){
        this.favoriteProducts.remove(product);
    }

    public void addAddress(Address address){
        BuyerAddress newBuyerAddress = new BuyerAddress(address);
        this.addresses.add(newBuyerAddress);
    }

    public void removeAddress(BuyerAddress address){
        this.addresses.remove(address);
    }

    //TODO Given that we will most probably use PUT for updates, a single update method is preferred.
    // it can either can all fields as parameter or a single value object. This is to be decided later which approach we'll use.
    public void updateEmail(String email){
        this.account.setEmail(email);
    }

    public void updateFirstName(String firstName){
        this.account.setFirstName(firstName);
    }

    public void updateLastName(String lastName){
        this.account.setLastName(lastName);
    }

    public void updatePasswordHash(String passwordHash){
        this.account.setPasswordHash(passwordHash);
    }

    public void updateImage(String image){
        this.account.setImageName(image);
    }

    public void updateTelephone(String telephone){
        this.account.setTelephone(telephone);
    }

    //todo toString() missing usually useful for debug purpose.
}
