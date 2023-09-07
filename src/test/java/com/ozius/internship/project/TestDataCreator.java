package com.ozius.internship.project;

import com.ozius.internship.project.entity.*;
import com.ozius.internship.project.entity.buyer.Buyer;
import com.ozius.internship.project.entity.cart.Cart;
import com.ozius.internship.project.entity.order.Order;
import com.ozius.internship.project.entity.seller.LegalDetails;
import com.ozius.internship.project.entity.seller.Review;
import com.ozius.internship.project.entity.seller.Seller;
import com.ozius.internship.project.entity.seller.SellerType;
import jakarta.persistence.EntityManager;
import org.springframework.security.crypto.password.PasswordEncoder;

public class TestDataCreator {

    public static void createBaseDataForProduct(EntityManager em, PasswordEncoder passwordEncoder) {
        createCategoriesBaseData(em);
        createSellerBaseData(em, passwordEncoder);
        createProductsBaseData(em);
    }

    public static void createBaseDataForReview(EntityManager em, PasswordEncoder passwordEncoder) {
        createBaseDataForProduct(em, passwordEncoder);
        createBuyerBaseData(em, passwordEncoder);
    }

    public static Buyer createBuyer(EntityManager em, UserAccount account){
        Buyer buyer = new Buyer(account);
        em.persist(buyer);

        return buyer;
    }

    public static void createBuyerBaseData(EntityManager em, PasswordEncoder passwordEncoder){
        UserAccount account1 = new UserAccount(
                "Cosmina",
                "Maria",
                "cosminamaria@gmail.com",
                "/src/image2",
                "0735897635");
        account1.setInitialPassword(passwordEncoder.encode("Ozius1234!"));
        Buyers.buyer1 = createBuyer(em, account1);

        UserAccount account2 = new UserAccount(
                "Marcel",
                "Danila",
                "marceldanila@gmail.com",
                "/src/image90",
                "0777777635");
        account2.setInitialPassword(passwordEncoder.encode("Ozius1234!"));
        Buyers.buyer2 = createBuyer(em, account2);

        UserAccount account3 = new UserAccount(
                "Vlad",
                "Ciobotariu",
                "vladciobotariu@gmail.com",
                "none",
                "+40770157915");
        account3.setInitialPassword(passwordEncoder.encode("Ozius1234!"));
        Buyers.buyer3 = createBuyer(em, account3);

    }

    public static Seller createSellerFarmer(EntityManager em, Address address, UserAccount account, String alias){
        Seller seller = new Seller(address, account, alias, SellerType.LOCAL_FARMER);
        em.persist(seller);

        return seller;
    }

    public static Seller createSellerCompany(EntityManager em, Address address, UserAccount account, String alias, SellerType sellerType, LegalDetails legalDetails){
        Seller seller = new Seller(address, account, alias, sellerType, legalDetails);
        em.persist(seller);

        return seller;
    }

    public static void  createSellerBaseData(EntityManager em, PasswordEncoder passwordEncoder){

        UserAccount account1 = new UserAccount("Vlad",
                "Ciobotariu",
                "vladciobotariu1@gmail.com",
                "/src/image1",
                "0734896512");
        account1.setInitialPassword(passwordEncoder.encode("Ozius1234!"));
        Sellers.seller1 = createSellerFarmer(em,
                new Address("Romania",
                        "Timis",
                        "Timisoara",
                        "Strada Circumvalatiunii nr 4",
                        "Bloc 3 Scara B Ap 12",
                        "303413"),
                account1,
                "Mega Fresh SRL"
        );

        UserAccount account2 = new UserAccount("Mihnea",
                "Mondialu",
                "mihneamondialu@gmail.com",
                "/src/image99",
                "0734896777");
        account2.setInitialPassword(passwordEncoder.encode("Ozius1234!"));
        Sellers.seller2 = createSellerFarmer(em,
                new Address("Spania",
                        "Granada",
                        "Barcelona",
                        "Strada Real Madrid nr 4",
                        "Bloc Cupa Romaniei",
                        "307773"),
                account2,
                "FC BARCELONA"
        );

    }

    private static Category createCategory(EntityManager em, String name, String image) {
        Category category = new Category(name, image);
        em.persist(category);
        return category;
    }

    public static void createCategoriesBaseData(EntityManager em){

        Categories.category1 = createCategory(em, "Fruits", "/images/fruits.svg");
        Categories.category2 = createCategory(em, "Vegetables", "/images/vegetables.svg");
        Categories.category3 = createCategory(em, "Dairy", "/images/dairy.svg");
        Categories.category4 = createCategory(em, "Nuts", "/images/nuts.svg");
        Categories.category5 = createCategory(em, "Honey", "/images/honey.svg");
        Categories.category6 = createCategory(em, "Sweets", "/images/sweets.svg");
        Categories.category7 = createCategory(em, "Oil", "/images/oil.svg");
        Categories.category8 = createCategory(em, "Tea", "/images/tea.svg");
        Categories.category9 = createCategory(em, "Juices", "/images/juice.svg");
    }

    public static Product createProduct(EntityManager em, String name, String description, String image, float price, Category category , Seller seller){

        Product product = new Product(name, description, image, price, category, seller);
        em.persist(product);

        return product;
    }

    public static void createProductsBaseData(EntityManager em){

        Products.product1 = createProduct(em, "Apple", "This is an apple! It is a fruit!", "/images/apple.jpeg", 12.7f, Categories.category1, Sellers.seller1);
        Products.product2 = createProduct(em, "Pear", "This is a pear! It is a fruit!", "/images/pear.png", 8.2f, Categories.category1, Sellers.seller1);
        Products.product3 = createProduct(em, "Kiwi", "This is a kiwi! It is a fruit!", "mages/kiwi.jpg", 5f, Categories.category1, Sellers.seller1);
        Products.product4 = createProduct(em, "Banana", "This is a banana! It is a fruit!", "/images/banana.jpg", 5f, Categories.category1, Sellers.seller1);
        Products.product5 = createProduct(em, "Mango", "This is a mango! It is a fruit!", "/images/mango.png", 5f, Categories.category1, Sellers.seller1);
        Products.product6 = createProduct(em, "Peach", "This is a peach! It is a fruit!", "/images/peach.png", 5f, Categories.category1, Sellers.seller1);
        Products.product7 = createProduct(em, "Orange", "This is an orange! It is a fruit!", "/images/orange.png", 5f, Categories.category1, Sellers.seller1);
        Products.product8 = createProduct(em, "Potato", "This is a potato! It is a vegetable!", "/images/orange.png", 5f, Categories.category2, Sellers.seller1);
    }

    public static void createAddresses(){
        Addresses.address1 = new Address("Romania", "Timis", "Timisoara", "Strada Macilor 10", "Bloc 4, Scara F, ap 50", "300091");
    }

    public static void createOrder(EntityManager em, Address address, Buyer buyer, Seller seller){

        Order order = new Order(
                address,
                buyer,
                seller,
                buyer.getAccount().getTelephone());

        em.persist(order);
    }


    public static Review createReview(Buyer buyer, String description, float rating, Product product){

        Seller seller = product.getSeller();

        return seller.addReview(buyer, description, rating, product);
    }

    private static Cart createCart(EntityManager em, Buyer buyer){
        Cart cart = new Cart(buyer);
        em.persist(cart);

        return cart;
    }

    private static void addItemToCart(EntityManager em, Cart cart, Product product, float quantity){
        Cart cartMerged = em.merge(cart);
        cartMerged.addOrUpdateItem(product, quantity);
    }

    public static void createCartBaseData(EntityManager em){
        Cart cart = createCart(em, Buyers.buyer3);

        addItemToCart(em, cart, Products.product1, 2.2F);
        addItemToCart(em, cart, Products.product2, 5.9F);
    }

    public static void createFavoritesBaseData(EntityManager em){
        Buyer buyer = em.merge(Buyers.buyer3);
        buyer.addFavorite(Products.product1);
        buyer.addFavorite(Products.product2);
    }

    public static class Buyers{
        public static Buyer buyer1;
        public static Buyer buyer2;
        public static Buyer buyer3;
    }

    public static class Sellers{
        public static Seller seller1;
        public static Seller seller2;
    }

    public static class Products{
        public static Product product1;
        public static Product product2;
        public static Product product3;
        public static Product product4;
        public static Product product5;
        public static Product product6;
        public static Product product7;
        public static Product product8;
    }

    public static class Categories{
        public static Category category1;
        public static Category category2;
        public static Category category3;
        public static Category category4;
        public static Category category5;
        public static Category category6;
        public static Category category7;
        public static Category category8;
        public static Category category9;
    }

    public static class Addresses{
        public static Address address1;
    }

}