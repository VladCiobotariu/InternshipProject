package com.ozius.internship.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = Product.TABLE_NAME)
public class Product extends BaseEntity {
    public static final String TABLE_NAME = "product";

    interface Columns {
        String NAME = "NAME";
        String DESCRIPTION = "DESCRIPTION";
        String IMAGE_NAME = "IMAGE_NAME";
        String PRICE = "PRICE";
        String CATEGORY_ID = "CATEGORY_ID";
        String SELLER_ID = "SELLER_ID";

    }

    @Column(name = Columns.NAME, nullable = false)
    private String name;

    @Column(name = Columns.DESCRIPTION, nullable = false)
    private String description;

    @Column(name = Columns.IMAGE_NAME, nullable = false)
    private String imageName;

    @Column(name = Columns.PRICE, nullable = false)
    private float price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Columns.CATEGORY_ID, nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Columns.SELLER_ID, nullable = false)
    private SellerInfo sellerInfo;

    public Product() {
    }

    public Product(String name, String description, String imageName, float price, Category category, SellerInfo sellerInfo) {
        this.name = name;
        this.description = description;
        this.imageName = imageName;
        this.price = price;
        this.category = category;
        this.sellerInfo = sellerInfo;
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

    public SellerInfo getSellerInfo() {
        return sellerInfo;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageName='" + imageName + '\'' +
                ", price=" + price +
                ", category=" + category +
                ", sellerInfo=" + sellerInfo +
                '}';
    }
}
