package com.ozius.internship.project.entity.seller;

import com.ozius.internship.project.entity.*;
import com.ozius.internship.project.entity.order.Order;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = Seller.TABLE_NAME)
public class Seller extends BaseEntity {

    public static final String TABLE_NAME = "SELLER";

    interface Columns{
        String ACCOUNT_ID = "ACCOUNT_ID";
        String ALIAS = "ALIAS";
        String COUNTRY = "COUNTRY";
        String STATE = "STATE";
        String CITY = "CITY";
        String ADDRESS_LINE_1 = "ADDRESS_LINE_1";
        String ADDRESS_LINE_2 = "ADDRESS_LINE_2";
        String ZIP_CODE = "ZIP_CODE";
    }

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "country", column = @Column(name = Columns.COUNTRY, nullable = false)),
            @AttributeOverride( name = "state", column = @Column(name = Columns.STATE, nullable = false)),
            @AttributeOverride( name = "city", column = @Column(name = Columns.CITY, nullable = false)),
            @AttributeOverride( name = "addressLine1", column = @Column(name = Columns.ADDRESS_LINE_1, nullable = false)),
            @AttributeOverride( name = "addressLine2", column = @Column(name = Columns.ADDRESS_LINE_2)),
            @AttributeOverride( name = "zipCode", column = @Column(name = Columns.ZIP_CODE, length = 6, nullable = false))
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

    public void removeReview(Review review) {
        this.reviews.remove(review);
    }

    public void updateSeller(String email, String firstName, String lastName, String passwordHash, String image, String telephone){
        this.account.setEmail(email);
        this.account.setFirstName(firstName);
        this.account.setLastName(lastName);
        this.account.setPasswordHash(passwordHash);
        this.account.setImageName(image);
        this.account.setTelephone(telephone);
    }

    //TODO remove after updating SellerEntityTest
    public void updateFirstName(String firstName){
        this.account.setFirstName(firstName);
    }

    @Override
    public String toString() {
        return "Seller{" +
                "alias='" + alias + '\'' +
                '}';
    }

}
