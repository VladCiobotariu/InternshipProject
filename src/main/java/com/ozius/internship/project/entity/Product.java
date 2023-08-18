package com.ozius.internship.project.entity;

import com.ozius.internship.project.entity.exception.IllegalDuplicateName;
import com.ozius.internship.project.entity.exception.IllegalPriceException;
import com.ozius.internship.project.entity.seller.Seller;
import jakarta.persistence.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
    @JoinColumn(name = Columns.SELLER_ID, nullable = false, foreignKey = @ForeignKey(foreignKeyDefinition = "FOREIGN KEY (" + Columns.SELLER_ID + ") REFERENCES " + Seller.TABLE_NAME + " (" + BaseEntity.ID + ")  ON DELETE CASCADE"))
    private Seller seller;

    protected static Map<Long, Set<String>> sellerProductNames = new HashMap<>();

    protected Product() {
    }

    public Product(String name, String description, String imageName, float price, Category category, Seller seller) {
        validateUniqueProductNameForSeller(seller.getId(), name);

        Set<String> productNames = sellerProductNames.get(seller.getId());
        productNames.add(name);

        this.name = name;
        this.description = description;
        this.imageName = imageName;
        if (price < 0) {
            throw new IllegalPriceException("Price cannot be negative!");
        }
        this.price = price;
        this.category = category;
        this.seller = seller;

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

    public void setPrice(float price) {
        this.price = price;
    }

    private void validateUniqueProductNameForSeller(Long sellerId, String productName) {
        if (!sellerProductNames.containsKey(sellerId)) {
            sellerProductNames.put(sellerId, new HashSet<>());
        }

        Set<String> productNames = sellerProductNames.get(sellerId);
        if (productNames.contains(productName)) {
            throw new IllegalDuplicateName("Seller already has a product with this name!");
        }
    }


    public void updateProduct(String name, String description, String imageName, float price, Category category, Seller seller) {
        validateUniqueProductNameForSeller(seller.getId(), this.getName());
        Set<String> productNames = sellerProductNames.get(seller.getId());
        productNames.remove(this.name);
        productNames.add(name);

        this.name = name;
        this.description = description;
        this.imageName = imageName;
        if (price < 0) {
            throw new IllegalPriceException("Price cannot be negative!");
        }
        this.price = price;
        this.category = category;
        this.seller = seller;
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
