package com.ozius.internship.project;

import com.ozius.internship.project.entity.*;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import org.aspectj.weaver.ast.Or;

import java.util.HashSet;
import java.util.Set;

public class TestDataCreator {

    public static void createBaseData(EntityManager em){
        createBuyerBaseData(em);
        createSellerBaseData(em);
        createCategoriesBaseData(em);
        createProductsBaseData(em);
    }

    public static BuyerInfo createBuyer(EntityManager em, UserAccount account){
        BuyerInfo buyer = new BuyerInfo(account);
        em.persist(buyer);

        return buyer;
    }

    public static void createBuyerBaseData(EntityManager em){
        createBuyer(em,
                new UserAccount(
                        "Cosmina",
                        "Maria",
                        "cosminamaria@gmail.com",
                        "ozius1223423345",
                        "/src/image2",
                        "0735897635"));

    }

    public static SellerInfo createSeller(EntityManager em, Address address, UserAccount account,  String alias){
        SellerInfo seller = new SellerInfo(address, account, alias);
        em.persist(seller);

        return seller;
    }

    public static void  createSellerBaseData(EntityManager em){
        createSeller(em,
                new Address("Romania",
                        "Timis",
                        "Timisoara",
                        "Strada Circumvalatiunii nr 4",
                        "Bloc 3 Scara B Ap 12",
                        "3003413"),
                new UserAccount("Vlad",
                        "Ciobotariu",
                        "vladciobotariu@gmail.com",
                        "ozius12345",
                        "/src/image1",
                        "0734896512"),
                "Mega Fresh SRL");
    }

    public static Category createCategory(EntityManager em, String name, String image) {
        Category category = new Category(name, image);
        em.persist(category);
        return category;
    }

    public static void createCategoriesBaseData(EntityManager em){
        createCategory(em, "Cereale", "image09");
    }

    public static Product createProduct(EntityManager em, String name, String description, String image, float price, long categoryId, long sellerId){
        Category category = em.find(Category.class, categoryId);
        SellerInfo seller = em.find(SellerInfo.class, sellerId);

        Product product = new Product(name, description, image, price, category, seller);
        em.persist(product);

        return product;
    }

    public static void createProductsBaseData(EntityManager em){
        createProduct(em, "orez", "pentru fiert", "src/image4", 12.7f, 1l, 1l);
        createProduct(em, "grau", "pentru paine", "src/image20", 8.2f, 1l, 1l);
    }

    public static void addAddressBuyer(EntityManager em, long buyerId, Address address){
        BuyerInfo buyer = em.find(BuyerInfo.class, buyerId);
        buyer.addAddress(address);
    }

    public static void createAddressBaseData(EntityManager em){
        addAddressBuyer(em, 1l, new Address("Romania", "Timis", "Timisoara", "Strada Macilor 10", "Bloc 4, Scara F, ap 50", "300091"));
    }

    public static OrderItem createOrderItem(EntityManager em, Product product, float quantity){
        OrderItem orderItem = new OrderItem(product, quantity);
        em.persist(orderItem);
        return orderItem;
    }

    public static void createOrderItemsBaseData(EntityManager em){
        Product product = em.find(Product.class, 1l);
        System.out.println(product.getName());
        createOrderItem(em, product, 5f);
    }

    public static Order createOrder(EntityManager em, Set<OrderItem> items, long buyerId, long sellerId){
        BuyerInfo buyer = em.find(BuyerInfo.class, buyerId);
        SellerInfo seller = em.find(SellerInfo.class, sellerId);
        Order order = new Order(buyer.getAddresses().stream().findFirst().get().getAddress(), buyer, seller, buyer.getAccount().getTelephone(), items);

        em.persist(order);

        return order;
    }

    public static void createOrdersBaseData(EntityManager em){
        Set<OrderItem> items = new HashSet<>();
        OrderItem orderItem = em.find(OrderItem.class, 1L);
        System.out.println(orderItem.getName());

        items.add(orderItem);

        createOrder(em, items, 1l, 1l);
    }
}
