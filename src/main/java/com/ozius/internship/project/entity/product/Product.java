package com.ozius.internship.project.entity.product;

import com.ozius.internship.project.entity.BaseEntity;
import com.ozius.internship.project.entity.Category;
import com.ozius.internship.project.entity.exception.IllegalPriceException;
import com.ozius.internship.project.entity.seller.Review;
import com.ozius.internship.project.entity.seller.Seller;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = Product.TABLE_NAME, uniqueConstraints = { @UniqueConstraint(columnNames = { Product.Columns.NAME, Product.Columns.SELLER_ID }) })
public class Product extends BaseEntity {
    public static final String TABLE_NAME = "product";

    interface Columns {
        String NAME = "NAME";
        String DESCRIPTION = "DESCRIPTION";
        String IMAGE_NAME = "IMAGE_NAME";
        String PRICE = "PRICE";
        String CATEGORY_ID = "CATEGORY_ID";
        String SELLER_ID = "SELLER_ID";
        String UNIT_OF_MEASURE = "UNIT_OF_MEASURE";
        String PRODUCT_RATING = "PRODUCT_RATING";
        String NUMBER_REVIEWS = "NUMBER_REVIEWS";
        String IS_RATING_APPLICABLE = "IS_RATING_APPLICABLE";
    }

    @Column(name = Columns.NAME, nullable = false)
    private String name;

    @Column(name = Columns.DESCRIPTION, nullable = false)
    private String description;

    @Column(name = Columns.IMAGE_NAME, nullable = false)
    private String imageName;

    @Column(name = Columns.PRICE, nullable = false)
    private float price;

    @Column(name = Columns.UNIT_OF_MEASURE, nullable = false)
    private UnitOfMeasure unitOfMeasure;

    @Column(name = Columns.PRODUCT_RATING)
    private Double productRating;

    @Column(name = Columns.NUMBER_REVIEWS)
    private int numberReviews;

    @Column(name = Columns.IS_RATING_APPLICABLE)
    private boolean isRatingApplicable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Columns.CATEGORY_ID, nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Columns.SELLER_ID, nullable = false, foreignKey = @ForeignKey(foreignKeyDefinition = "FOREIGN KEY (" + Columns.SELLER_ID + ") REFERENCES " + Seller.TABLE_NAME + " (" + BaseEntity.ID + ")  ON DELETE CASCADE"))
    private Seller seller;


    protected Product() {
    }

    public Product(String name, String description, String imageName, float price, Category category, Seller seller, UnitOfMeasure unitOfMeasure) {
        this.name = name;
        this.description = description;
        this.imageName = imageName;
        if (price < 0) {
            throw new IllegalPriceException("Price cannot be negative!");
        }
        this.price = price;
        this.category = category;
        this.seller = seller;
        this.unitOfMeasure = unitOfMeasure;
        this.productRating = 0.0;
        this.numberReviews = 0;
        this.isRatingApplicable = false;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageName() {
        return imageName;
    }

    public float getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    public Seller getSeller() {
        return seller;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public Double getProductRating() {
        return productRating;
    }

    public long getNumberReviews() {
        return numberReviews;
    }

    public boolean isRatingApplicable() {
        return isRatingApplicable;
    }

    public Double calculateProductRating(List<Review> reviews) {
        return reviews.stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);
    }

    public long calculateNumberReviews(List<Review> reviews) {
        return reviews.size();
    }

    public void updateRatingInformation(List<Review> reviews) {
        this.productRating = calculateProductRating(reviews);
        this.numberReviews = (int) calculateNumberReviews(reviews);
        this.isRatingApplicable = numberReviews > 2;
    }

    public void updateProduct(String name, String description, String imageName, float price, Category category, Seller seller, UnitOfMeasure unitOfMeasure) {
        this.name = name;
        this.description = description;
        this.imageName = imageName;
        if (price < 0) {
            throw new IllegalPriceException("Price cannot be negative!");
        }
        this.price = price;
        this.category = category;
        this.seller = seller;
        this.unitOfMeasure = unitOfMeasure;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageName='" + imageName + '\'' +
                ", price=" + price +
                ", category=" + category.getName() +
                ", sellerInfo=" + seller.getAlias() +
                '}';
    }
}
