package com.ozius.internship.project.entity;

import jakarta.persistence.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = Seller.TABLE_NAME)
public class Seller extends BaseEntity{

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


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = Review.Columns.SELLER_ID)
    private Set<Review> reviews;

    @Column(name = Columns.ALIAS, nullable = false)
    private String alias;

    public Seller() {
    }

    public Seller(Address legalAddress, UserAccount account, String alias) {
        this.legalAddress = legalAddress;
        this.account = account;
        this.reviews = new HashSet<>();
        this.alias = alias;
    }

    public Address getLegalAddress() {
        return legalAddress;
    }

    public UserAccount getAccount() {
        return account;
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

    public Review addReview(Buyer buyer, String description, float rating, Product product){

        Review reviewNew = new Review(description, rating, buyer, product);
        this.reviews.add(reviewNew);

        return reviewNew;
    }

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

    @Override
    public String toString() {
        return "Seller{" +
                "alias='" + alias + '\'' +
                '}';
    }
}
