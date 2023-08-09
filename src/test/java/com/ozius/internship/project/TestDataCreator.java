package com.ozius.internship.project;

import com.ozius.internship.project.entity.*;
import com.ozius.internship.project.entity.order.Order;
import com.ozius.internship.project.entity.order.OrderItem;
import com.ozius.internship.project.entity.seller.Review;
import com.ozius.internship.project.entity.seller.Seller;
import jakarta.persistence.EntityManager;
import org.aspectj.weaver.ast.Or;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestDataCreator {

    public static void createBaseData(EntityManager em){
        createBuyerBaseData(em);
        createSellerBaseData(em);
        createCategoriesBaseData(em);
        createProductsBaseData(em);
    }

    private static Buyer createBuyer(EntityManager em, UserAccount account){
        Buyer buyer = new Buyer(account);
        em.persist(buyer);

        return buyer;
    }

    public static void createBuyerBaseData(EntityManager em){
        Buyers.buyer = createBuyer(em,
                new UserAccount(
                        "Cosmina",
                        "Maria",
                        "cosminamaria@gmail.com",
                        "ozius1223423345",
                        "/src/image2",
                        "0735897635"));

    }

    private static Seller createSeller(EntityManager em, Address address, UserAccount account, String alias){
        Seller seller = new Seller(address, account, alias);
        em.persist(seller);

        return seller;
    }

    public static void  createSellerBaseData(EntityManager em){
        Sellers.seller = createSeller(em,
                new Address("Romania",
                        "Timis",
                        "Timisoara",
                        "Strada Circumvalatiunii nr 4",
                        "Bloc 3 Scara B Ap 12",
                        "303413"),
                new UserAccount("Vlad",
                        "Ciobotariu",
                        "vladciobotariu@gmail.com",
                        "ozius12345",
                        "/src/image1",
                        "0734896512"),
                "Mega Fresh SRL");
    }

    private static Category createCategory(EntityManager em, String name, String image) {
        Category category = new Category(name, image);
        em.persist(category);
        return category;
    }

    public static void createCategoriesBaseData(EntityManager em){

        Categories.category = createCategory(em, "Cereale", "image09");
    }

    public static Product createProduct(EntityManager em, String name, String description, String image, float price, Category category , Seller seller){

        Product product = new Product(name, description, image, price, category, seller);
        em.persist(product);

        return product;
    }

    public static void createProductsBaseData(EntityManager em){

        Products.product1 = createProduct(em, "orez", "pentru fiert", "src/image4", 12.7f, Categories.category, Sellers.seller);
        Products.product2 = createProduct(em, "grau", "pentru paine", "src/image20", 8.2f, Categories.category, Sellers.seller);
    }

    private static void addAddressBuyer(EntityManager em, Buyer buyer, Address address){
        buyer.addAddress(address);
    }

    public static void createAddressBaseData(EntityManager em){
        addAddressBuyer(em, Buyers.buyer, new Address("Romania", "Timis", "Timisoara", "Strada Macilor 10", "Bloc 4, Scara F, ap 50", "300091"));
    }

    private static void createOrderItem(EntityManager em, Order order, Product product, float quantity){
        order.addProduct(product, quantity);
    }

    private static Order createOrder(EntityManager em, Buyer buyer, Seller seller){

        Order order = new Order(buyer.getAddresses().stream().findFirst().get().getAddress(), buyer, seller, buyer.getAccount().getTelephone());

        em.persist(order);

        return order;
    }

    public static void createOrdersBaseData(EntityManager em){

        Orders.order = createOrder(em, Buyers.buyer, Sellers.seller);

        createOrderItem(em, Orders.order, Products.product1, 5f);
        createOrderItem(em, Orders.order, Products.product2, 2f);

    }

    private static Review createReview(EntityManager em, Buyer buyer, String description, float rating, Product product){

        Seller seller = product.getSeller();

        Review review = seller.addReview(buyer, description, rating, product);
        em.flush();

        return review;
    }

    public static void createReviewBaseData(EntityManager em){
        Reviews.review1 = createReview(em, Buyers.buyer, "very good review89", 4f, Products.product1);
        Reviews.review2 = createReview(em, Buyers.buyer, "bad review", 1.5f, Products.product2);
    }

    public static class Buyers{
        public static Buyer buyer;
    }

    public static class Sellers{
        public static Seller seller;
    }

    public static class Orders{
        public static Order order;
    }

    public static class Products{
        public static Product product1;
        public static Product product2;
    }

    public static class Categories{
        public static Category category;
    }

    public static class Reviews{
        public static Review review1;
        public static Review review2;
    }

}