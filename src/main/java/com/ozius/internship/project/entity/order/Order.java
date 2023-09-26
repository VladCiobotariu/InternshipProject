package com.ozius.internship.project.entity.order;

import com.ozius.internship.project.entity.Address;
import com.ozius.internship.project.entity.BaseEntity;
import com.ozius.internship.project.entity.buyer.Buyer;
import com.ozius.internship.project.entity.exception.IllegalItemException;
import com.ozius.internship.project.entity.exception.IllegalOrderState;
import com.ozius.internship.project.entity.product.Product;
import com.ozius.internship.project.entity.seller.LegalDetails;
import com.ozius.internship.project.entity.seller.Seller;
import com.ozius.internship.project.entity.seller.SellerType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = Order.TABLE_NAME)
public class Order extends BaseEntity {

    public static final String TABLE_NAME = "CUSTOMER_ORDER";

    interface Columns {
        String BUYER_EMAIL = "BUYER_EMAIL";
        String BUYER_FIRST_NAME = "BUYER_FIRST_NAME";
        String BUYER_LAST_NAME = "BUYER_LAST_NAME";
        String SELLER_ID = "SELLER_ID";
        String BUYER_ID = "BUYER_ID";
        String TELEPHONE = "TELEPHONE";
        String ORDER_STATUS = "ORDER_STATUS";
        String ORDER_DATE = "ORDER_DATE";
        String TOTAL_PRICE = "TOTAL_PRICE";
        String COUNTRY = "COUNTRY";
        String STATE = "STATE";
        String CITY = "CITY";
        String ADDRESS_LINE_1 = "ADDRESS_LINE_1";
        String ADDRESS_LINE_2 = "ADDRESS_LINE_2";
        String ZIP_CODE = "ZIP_CODE";
        String SELLER_EMAIL = "SELLER_EMAIL";
        String SELLER_ALIAS = "SELLER_ALIAS";
        String COMPANY_NAME = "COMPANY_NAME";
        String CUI = "CUI";
        String COMPANY_TYPE = "COMPANY_TYPE";
        String NUMERIC_CODE_BY_STATE = "NUMERIC_CODE_BY_STATE";
        String SERIAL_NUMBER = "SERIAL_NUMBER";
        String DATE_OF_REGISTRATION = "DATE_OF_REGISTRATION";
        String SELLER_TYPE = "SELLER_TYPE";
    }

    @Enumerated(EnumType.STRING)
    @Column(name = Columns.ORDER_STATUS, nullable = false)
    private OrderStatus orderStatus;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "country", column = @Column(name = Columns.COUNTRY, nullable = false)),
            @AttributeOverride(name = "state", column = @Column(name = Columns.STATE, nullable = false)),
            @AttributeOverride(name = "city", column = @Column(name = Columns.CITY, nullable = false)),
            @AttributeOverride(name = "addressLine1", column = @Column(name = Columns.ADDRESS_LINE_1, nullable = false)),
            @AttributeOverride(name = "addressLine2", column = @Column(name = Columns.ADDRESS_LINE_2)),
            @AttributeOverride(name = "zipCode", column = @Column(name = Columns.ZIP_CODE, length = 6, nullable = false))
    })
    private Address shippingAddress;

    @ManyToOne
    @JoinColumn(name = Columns.BUYER_ID, foreignKey = @ForeignKey(foreignKeyDefinition = "FOREIGN KEY (" + Columns.BUYER_ID + ") REFERENCES " + Buyer.TABLE_NAME + " (" + BaseEntity.ID + ")  ON DELETE SET NULL"))
    private Buyer buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Columns.SELLER_ID, foreignKey = @ForeignKey(foreignKeyDefinition = "FOREIGN KEY (" + Columns.SELLER_ID + ") REFERENCES " + Seller.TABLE_NAME + " (" + BaseEntity.ID + ")  ON DELETE SET NULL"))
    private Seller seller;

    @Column(name = Columns.SELLER_EMAIL, nullable = false)
    private String sellerEmail;

    @Column(name = Columns.SELLER_ALIAS, nullable = false)
    private String sellerAlias;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = Columns.COMPANY_NAME)),
            @AttributeOverride(name = "cui", column = @Column(name = Columns.CUI, length = 10)),
            @AttributeOverride(name = "registrationNumber.companyType", column = @Column(name = Columns.COMPANY_TYPE, length = 10)),
            @AttributeOverride(name = "registrationNumber.numericCodeByState", column = @Column(name = Columns.NUMERIC_CODE_BY_STATE, length = 10)),
            @AttributeOverride(name = "registrationNumber.serialNumber", column = @Column(name = Columns.SERIAL_NUMBER, length = 10)),
            @AttributeOverride(name = "registrationNumber.dateOfRegistration", column = @Column(name = Columns.DATE_OF_REGISTRATION, length = 10))
    })
    private LegalDetails legalDetails;

    @Enumerated(EnumType.STRING)
    @Column(name = Columns.SELLER_TYPE)
    private SellerType sellerType;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = OrderItem.Columns.ORDER_ID, foreignKey = @ForeignKey(foreignKeyDefinition =
            "FOREIGN KEY (" + OrderItem.Columns.ORDER_ID + ") REFERENCES " + Order.TABLE_NAME + " (" + BaseEntity.ID + ")  ON DELETE CASCADE"))
    private Set<OrderItem> orderItems;

    @Column(name = Columns.BUYER_EMAIL, nullable = false)
    private String buyerEmail;

    @Column(name = Columns.BUYER_FIRST_NAME, nullable = false)
    private String buyerFirstName;

    @Column(name = Columns.BUYER_LAST_NAME, nullable = false)
    private String buyerLastName;

    @Column(name = Columns.ORDER_DATE, nullable = false)
    private LocalDateTime orderDate;

    @Column(name = Columns.TELEPHONE, nullable = false, length = 12)
    private String telephone;

    @Column(name = Columns.TOTAL_PRICE, nullable = false)
    private float totalPrice;

    protected Order() {
    }

    public Order(Address shippingAddress, Buyer buyer, Seller seller, String buyerEmail, String buyerFirstName, String buyerLastName, String buyerTelephone) {
        this.orderStatus = OrderStatus.DRAFT;

        this.shippingAddress = shippingAddress;

        this.buyerFirstName = buyerFirstName;
        this.buyerLastName = buyerLastName;

        this.buyer = buyer;
        this.seller = seller;

        this.orderItems = new HashSet<>();

        this.sellerEmail = seller.getAccount().getEmail();
        this.sellerAlias = seller.getAlias();
        this.legalDetails = seller.getLegalDetails();
        this.sellerType = seller.getSellerType();

        this.buyerEmail = buyerEmail;
        this.orderDate = LocalDateTime.now();

        this.telephone = buyerTelephone;

        this.totalPrice = 0f;
    }

    public OrderItem addProduct(Product product, float quantity) {

        if(this.orderItems.stream().map(OrderItem::getProduct).anyMatch(item -> item.equals(product))){
            throw new IllegalItemException("can't add this item, already added");
        }

        if (!(product.getSeller().equals(this.getSeller()))) {
            throw new IllegalItemException("can't add this item, it belongs to different seller");
        }

        if (this.orderStatus == OrderStatus.SUBMITTED ||
                this.orderStatus == OrderStatus.SHIPPED ||
                this.orderStatus == OrderStatus.DELIVERED) {
            throw new IllegalOrderState("can't add item, order already processed");
        }

        OrderItem newItem = new OrderItem(product, quantity);
        this.orderItems.add(newItem);

        this.totalPrice = (float) this.orderItems
                .stream()
                .mapToDouble(this::calculateItemPrice)
                .sum();

        return newItem;
    }

    private float calculateItemPrice(OrderItem orderItem) {
        return orderItem.getQuantity() * orderItem.getItemPrice();
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public Seller getSeller() {
        return seller;
    }

    public Set<OrderItem> getOrderItems() {
        return Collections.unmodifiableSet(orderItems);
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public String getTelephone() {
        return telephone;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public String getSellerAlias(){
        return sellerAlias;
    }

    public LegalDetails getLegalDetails() {
        return legalDetails;
    }

    public SellerType getSellerType() {
        return sellerType;
    }

    public String getBuyerFirstName() {
        return buyerFirstName;
    }

    public String getBuyerLastName() {
        return buyerLastName;
    }

    public void submit() {
        if (this.orderStatus != OrderStatus.DRAFT) {
            throw new IllegalOrderState("order state can only be draft if you want to submit");
        } else if (this.orderItems.isEmpty()) {
            throw new IllegalOrderState("order doesn't have any items, please add items to submit");
        }
        this.orderStatus = OrderStatus.SUBMITTED;
    }

    public void markedAsShipped() {
        if (this.orderStatus != OrderStatus.SUBMITTED) {
            throw new IllegalOrderState("order state can only be submitted if you want to ship");
        }
        this.orderStatus = OrderStatus.SHIPPED;
    }

    public void markedAsDelivered() {
        if (this.orderStatus != OrderStatus.SHIPPED) {
            throw new IllegalOrderState("order state can only be shipped if you want to deliver");
        }
        this.orderStatus = OrderStatus.DELIVERED;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderStatus=" + orderStatus +
                ", address=" + shippingAddress +
                ", buyer=" + buyer +
                ", seller=" + seller +
                ", sellerEmail='" + sellerEmail + '\'' +
                ", sellerAlias='" + sellerAlias + '\'' +
                ", legalDetails=" + legalDetails +
                ", sellerType=" + sellerType +
                ", orderItems=" + orderItems +
                ", buyerEmail='" + buyerEmail + '\'' +
                ", buyerFirstName='" + buyerFirstName + '\'' +
                ", buyerLastName='" + buyerLastName + '\'' +
                ", orderDate=" + orderDate +
                ", telephone='" + telephone + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
