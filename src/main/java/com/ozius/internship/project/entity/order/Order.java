package com.ozius.internship.project.entity.order;

import com.ozius.internship.project.entity.*;
import com.ozius.internship.project.entity.exeption.IllegalItemExeption;
import com.ozius.internship.project.entity.exeption.IllegalOrderState;
import com.ozius.internship.project.entity.seller.Seller;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = Order.TABLE_NAME)
public class Order extends BaseEntity {

    public static final String TABLE_NAME = "[ORDER]";

    interface Columns{
        String BUYER_EMAIL = "BUYER_EMAIL";
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
    }

    @Enumerated(EnumType.STRING)
    @Column(name = Columns.ORDER_STATUS, nullable = false)
    private OrderStatus orderStatus;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "country", column = @Column(name = Columns.COUNTRY, nullable = false)),
            @AttributeOverride( name = "state", column = @Column(name = Columns.STATE, nullable = false)),
            @AttributeOverride( name = "city", column = @Column(name = Columns.CITY, nullable = false)),
            @AttributeOverride( name = "addressLine1", column = @Column(name = Columns.ADDRESS_LINE_1, nullable = false)),
            @AttributeOverride( name = "addressLine2", column = @Column(name = Columns.ADDRESS_LINE_2)),
            @AttributeOverride( name = "zipCode", column = @Column(name = Columns.ZIP_CODE, length = 6, nullable = false))
    })
    private Address address;

    @ManyToOne
    @JoinColumn(name = Columns.BUYER_ID, foreignKey = @ForeignKey(foreignKeyDefinition = "FOREIGN KEY (" + Columns.BUYER_ID + ") REFERENCES " + Buyer.TABLE_NAME + " (" + BaseEntity.ID + ")  ON DELETE SET NULL"))
    private Buyer buyer;

    @ManyToOne(fetch = FetchType.LAZY) //TODO Seller info more than email?
    @JoinColumn(name = Columns.SELLER_ID, foreignKey = @ForeignKey(foreignKeyDefinition = "FOREIGN KEY (" + Columns.SELLER_ID + ") REFERENCES " + Seller.TABLE_NAME + " (" + BaseEntity.ID + ")  ON DELETE SET NULL"))
    private Seller seller;

    @Column(name = Columns.SELLER_EMAIL, nullable = false)
    private String sellerEmail;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = OrderItem.Columns.ORDER_ID)
    private Set<OrderItem> orderItems;

    @Column(name = Columns.BUYER_EMAIL, nullable = false)
    private String buyerEmail;

    @Column(name = Columns.ORDER_DATE, nullable = false)
    private LocalDateTime orderDate;

    @Column(name = Columns.TELEPHONE, nullable = false, length = 10)
    private String telephone;

    @Column(name = Columns.TOTAL_PRICE, nullable = false)
    private float totalPrice;

    protected Order() {
    }

    public Order(Address address, Buyer buyer, Seller seller, String telephone) {
        this.orderStatus = OrderStatus.DRAFT;

        this.address = address;

        this.buyer = buyer;
        //TODO is it necessary to add seller to constructor or can we set it in addProduct if seller is null?
        this.seller = seller;

        this.orderItems = new HashSet<>();

        this.sellerEmail = seller.getAccount().getEmail();
        this.buyerEmail = buyer.getAccount().getEmail();
        this.orderDate = LocalDateTime.now();

        this.telephone = telephone;

        this.totalPrice = 0f;
    }

    public OrderItem addProduct(Product product, float quantity){
        if(! (product.getSeller().equals(this.getSeller())) ){
            throw new IllegalItemExeption("can't add this item, it belongs to different seller");
        }

        if(this.orderStatus==OrderStatus.SUBMITTED ||
                this.orderStatus==OrderStatus.SHIPPED ||
                this.orderStatus==OrderStatus.DELIVERED){
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

    public Address getAddress() {
        return address;
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

    public void submit(){
        if(this.orderStatus != OrderStatus.DRAFT) {
            throw new IllegalOrderState("order state can only be draft if you want to submit");
        } else if (this.orderItems.isEmpty()) {
            throw new IllegalOrderState("order doesn't have any items, please add items to submit");
        }
        this.orderStatus = OrderStatus.SUBMITTED;
    }

    //TODO test
    public void markedAsShipped(){
        if(this.orderStatus != OrderStatus.SUBMITTED){
            throw new IllegalOrderState("order state can only be submitted if you want to ship");
        }
        this.orderStatus = OrderStatus.SHIPPED;
    }

    //TODO test
    public void markedAsDelivered(){
        if(this.orderStatus != OrderStatus.SHIPPED){
            throw new IllegalOrderState("order state can only be shipped if you want to deliver");
        }
        this.orderStatus = OrderStatus.DELIVERED;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderStatus=" + orderStatus +
                ", address=" + address +
                ", buyerEmail='" + buyerEmail + '\'' +
                ", orderDate=" + orderDate +
                ", telephone='" + telephone + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
