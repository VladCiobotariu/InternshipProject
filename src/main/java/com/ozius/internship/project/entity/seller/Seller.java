package com.ozius.internship.project.entity.seller;

import com.ozius.internship.project.entity.*;
import com.ozius.internship.project.entity.buyer.Buyer;
import com.ozius.internship.project.entity.exception.IllegalSellerDetails;
import com.ozius.internship.project.entity.exception.IllegalItemException;
import com.ozius.internship.project.entity.exception.IllegalRatingException;

import com.ozius.internship.project.entity.product.Product;
import com.ozius.internship.project.entity.DomainEventPublisherProvider;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = Seller.TABLE_NAME, uniqueConstraints = {
        @UniqueConstraint(columnNames =
            { Seller.Columns.COMPANY_NAME, Seller.Columns.CUI, Seller.Columns.COMPANY_TYPE, Seller.Columns.NUMERIC_CODE_BY_STATE, Seller.Columns.SERIAL_NUMBER, Seller.Columns.DATE_OF_REGISTRATION }),
        @UniqueConstraint(columnNames =
            { Seller.Columns.COUNTRY, Seller.Columns.STATE, Seller.Columns.CITY, Seller.Columns.ADDRESS_LINE_1, Seller.Columns.ADDRESS_LINE_2, Seller.Columns.ZIP_CODE})})
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
        String SELLER_TYPE = "SELLER_TYPE";
        String COMPANY_NAME = "COMPANY_NAME";
        String CUI = "CUI";
        String COMPANY_TYPE = "COMPANY_TYPE";
        String NUMERIC_CODE_BY_STATE = "NUMERIC_CODE_BY_STATE";
        String SERIAL_NUMBER = "SERIAL_NUMBER";
        String DATE_OF_REGISTRATION = "DATE_OF_REGISTRATION";
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

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = Columns.COMPANY_NAME)),
            @AttributeOverride(name = "cui", column = @Column(name = Columns.CUI, length = 10, unique = true)),
            @AttributeOverride(name = "registrationNumber.companyType", column = @Column(name = Columns.COMPANY_TYPE)),
            @AttributeOverride(name = "registrationNumber.numericCodeByState", column = @Column(name = Columns.NUMERIC_CODE_BY_STATE)),
            @AttributeOverride(name = "registrationNumber.serialNumber", column = @Column(name = Columns.SERIAL_NUMBER)),
            @AttributeOverride(name = "registrationNumber.dateOfRegistration", column = @Column(name = Columns.DATE_OF_REGISTRATION))
    })
    private LegalDetails legalDetails;

    @Enumerated(EnumType.STRING)
    @Column(name = Columns.SELLER_TYPE, nullable = false)
    private SellerType sellerType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = Columns.ACCOUNT_ID, nullable = false)
    private UserAccount account;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = Review.Columns.SELLER_ID, nullable = false, foreignKey = @ForeignKey(foreignKeyDefinition =
            "FOREIGN KEY (" + Review.Columns.SELLER_ID + ") REFERENCES " + Seller.TABLE_NAME + " (" + BaseEntity.ID + ")  ON DELETE CASCADE"))
    private Set<Review> reviews;

    @Column(name = Columns.ALIAS, nullable = false, unique = true)
    private String alias;

    protected Seller() {
    }

    public Seller(Address legalAddress, UserAccount account, String alias, SellerType sellerType, LegalDetails legalDetails) {
        this.legalAddress = legalAddress;
        this.account = account;
        this.reviews = new HashSet<>();
        this.alias = alias;
        this.sellerType = sellerType;
        if(sellerType != SellerType.LOCAL_FARMER){
            if(legalDetails==null) throw new IllegalSellerDetails("legalDetails can't be null if company or pfa");
            this.legalDetails = legalDetails;
        }
    }

    public Seller(Address legalAddress, UserAccount account, String alias, SellerType sellerType) {
        this.legalAddress = legalAddress;
        this.account = account;
        this.reviews = new HashSet<>();
        this.alias = alias;
        this.sellerType = sellerType;
    }

    public Address getLegalAddress() {
        return legalAddress;
    }

    public String getCity() {
        return legalAddress.getCity();
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

    public LegalDetails getLegalDetails() {
        return legalDetails;
    }

    public SellerType getSellerType() {
        return sellerType;
    }

    public double calculateRating(){
        return this.reviews.stream().mapToDouble(Review::getRating).average().orElse(0.0);
    }

    public Review addReview(Buyer buyer, String description, float rating, Product product){

        if(!product.getSeller().equals(this)){
            throw new IllegalItemException("can't add review, product must correspond to this seller");
        }
        if(rating < 0 || rating > 5) {
            throw new IllegalRatingException("Rating must be between 0 and 5!");
        }

        Review reviewNew = new Review(description, rating, buyer, product);
        this.reviews.add(reviewNew);

        DomainEventPublisherProvider.getEventPublisher().publishEvent(new ReviewAddedEvent(product.getId()));
        // todo - reviews shouldnt be tight to seller anymore, so when we add a review we will have another listener that will add the review in its repository
        return reviewNew;
    }

    public void updateSeller(LegalDetails legalDetails, Address legalAddress ){
        this.legalDetails = legalDetails;
        this.legalAddress = legalAddress;
    }

    @Override
    public String toString() {
        return "Seller{" +
                "alias='" + alias + '\'' +
                '}';
    }

}
