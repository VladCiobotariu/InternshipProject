package com.ozius.internship.project.entity;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = SellerInfo.TABLE_NAME)
public class SellerInfo extends BaseEntity{

    public static final String TABLE_NAME = "SELLER_INFO";

    interface Columns{
        String ACCOUNT_ID = "ACCOUNT_ID";
        String ALIAS = "ALIAS";
    }

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "country", column = @Column(name = "COUNTRY")),
            @AttributeOverride( name = "state", column = @Column(name = "STATE")),
            @AttributeOverride( name = "city", column = @Column(name = "CITY")),
            @AttributeOverride( name = "addressLine1", column = @Column(name = "ADDRESS_LINE_1")),
            @AttributeOverride( name = "addressLine2", column = @Column(name = "ADDRESS_LINE_2")),
            @AttributeOverride( name = "zipCode", column = @Column(name = "ZIP_CODE"))
    })
    private Address legalAddress;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = Columns.ACCOUNT_ID, nullable = false)
    private UserAccount account;

    @OneToMany(mappedBy = "sellerInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Product> products;

    @OneToMany(mappedBy = "sellerInfo")
    private Set<Review> reviews;

    @Column(name = Columns.ALIAS, nullable = false)
    private String alias;

    public SellerInfo() {
    }

    public SellerInfo(Address legalAddress, UserAccount account, String alias) {
        this.legalAddress = legalAddress;
        this.account = account;
        this.products = new HashSet<>();
        this.reviews = new HashSet<>();
        this.alias = alias;
    }

    public Address getLegalAddress() {
        return legalAddress;
    }

    public UserAccount getAccount() {
        return account;
    }

    public Set<Product> getProducts() {
        return Collections.unmodifiableSet(products);
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public String getAlias() {
        return alias;
    }

    public double calculateRating(){
        return this.reviews.stream().mapToDouble(Review::getRating).average().orElse(0.0);
    }

    public void addReview(BuyerInfo buyer, String description, float rating, Product product){
        this.reviews.add(new Review(description, rating, buyer, this, product));
    }

    @Override
    public String toString() {
        return "SellerInfo{" +
                "alias='" + alias + '\'' +
                '}';
    }
}
