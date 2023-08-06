package com.ozius.internship.project;

import com.ozius.internship.project.entity.*;
import jakarta.persistence.EntityManager;

import java.util.HashSet;
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
                        "3003413"),
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

    private static Product createProduct(EntityManager em, String name, String description, String image, float price, long categoryId, long sellerId){
        //TODO product factory method should allow creation of products with configurable category and seller.
        Category category = Categories.category;
        Seller seller = Sellers.seller;

        Product product = new Product(name, description, image, price, category, seller);
        em.persist(product);

        return product;
    }

    public static void createProductsBaseData(EntityManager em){
        //TODO please avoid using hardcoded entity IDS. Either use static business keys or entity references.
        Products.product1 = createProduct(em, "orez", "pentru fiert", "src/image4", 12.7f, 1l, 1l);
        Products.product2 = createProduct(em, "grau", "pentru paine", "src/image20", 8.2f, 1l, 1l);
    }

    private static void addAddressBuyer(EntityManager em, long buyerId, Address address){
        Buyer buyer = Buyers.buyer;
        buyer.addAddress(address);
    }

    public static void createAddressBaseData(EntityManager em){
        addAddressBuyer(em, 1l, new Address("Romania", "Timis", "Timisoara", "Strada Macilor 10", "Bloc 4, Scara F, ap 50", "300091"));
    }

    private static OrderItem createOrderItem(EntityManager em, Product product, float quantity){
        OrderItem orderItem = new OrderItem(product, quantity);
        return orderItem;
    }

    private static Set<OrderItem> createOrderItems(EntityManager em){
        Product product1 = Products.product1;
        Product product2 = Products.product2;

        OrderItems.orderItem1 = createOrderItem(em, product1, 5f);
        OrderItems.orderItem2 = createOrderItem(em, product2, 7f);

        Set<OrderItem> items = new HashSet<>();
        items.add(OrderItems.orderItem1);
        items.add(OrderItems.orderItem2);

        return items;
    }

    private static Order createOrder(EntityManager em, Set<OrderItem> items, long buyerId, long sellerId){
        Buyer buyer = Buyers.buyer;
        Seller seller = Sellers.seller;

        Order order = new Order(buyer.getAddresses().stream().findFirst().get().getAddress(), buyer, seller, buyer.getAccount().getTelephone(), items);

        em.persist(order);

        return order;
    }

    public static void createOrdersBaseData(EntityManager em){

        Orders.order = createOrder(em, createOrderItems(em), 1l, 1l);

    }

    private static Review createReview(EntityManager em, long buyerId, String description, float rating, long productId){
        Buyer buyer = Buyers.buyer;
        Product product = Products.product1;
        Seller seller = product.getSeller();

        Review review = seller.addReview(buyer, description, rating, product);
        em.flush();

        return review;
    }

    public static void createReviewBaseData(EntityManager em){
        Reviews.review1 = createReview(em, 1l, "very good review89", 4f, 1l);
        Reviews.review2 = createReview(em, 1l, "bad review", 1.5f, 2l);
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

    public static class OrderItems{
        public static OrderItem orderItem1;
        public static OrderItem orderItem2;
    }

}