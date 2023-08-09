package com.ozius.internship.project;

import com.ozius.internship.project.entity.*;
import com.ozius.internship.project.entity.cart.Cart;
import com.ozius.internship.project.entity.cart.CartItem;
import com.ozius.internship.project.entity.seller.Review;
import com.ozius.internship.project.entity.seller.Seller;
import jakarta.persistence.EntityManager;

//TODO there should be only one test data creator shared by all devs. Please merge to main test data creator.
public class TestDataCreatorErika {

    public static void createBaseDataForProduct(EntityManager em) {
        createCategoriesBaseData(em);
        createSellerBaseData(em);
        createProductsBaseData(em);
    }

    public static void createBaseDataForReview(EntityManager em) {
        createBaseDataForProduct(em);
        createBuyersBaseData(em);
    }

    public static Category createCategory(String name, String imageName, EntityManager em) {
        Category category = new Category(name, imageName);
        em.persist(category);
        return category;
    }

    public static void createCategoriesBaseData(EntityManager em) {
        Categories.category1 = createCategory("legume", "/legume", em);
        Categories.category2 = createCategory("fructe", "/fructe", em);
        Categories.category3 = createCategory("congelate", "/congelate", em);
    }

    public static Seller createSeller(Address addr, UserAccount userAccount, String alias, EntityManager em) {
        Seller seller = new Seller(addr, userAccount, alias);
        em.persist(seller);
        return seller;
    }

    public static void createSellerBaseData(EntityManager em) {
        Sellers.seller1 = createSeller(new Address("Ro", "MM", "BM", "vl", "6/8","245"),
                new UserAccount("Erika", "Rusz", "e@gmail.com", "1234", "imageNameAcc", "0747871208"),
                "aliasBM", em);
        Sellers.seller2 = createSeller(new Address("Ro", "TM", "TM", "vl", "6/8","245"),
                new UserAccount("Alex", "Dulfu", "a@gmail.com", "1234", "imageNameAcc", "0747871208"),
                "aliasTM", em);
    }

    public static Product createProduct(String name, String description, String imageName, float price, Category cat, Seller seller, EntityManager em) {
        Product product = new Product(name, description, imageName, price, cat, seller);
        em.persist(product);
        return product;
    }

    public static void createProductsBaseData(EntityManager em) {
        Products.product1 = createProduct("rosii", "descriereRosii", "/rosii", 2.5F, Categories.category1, Sellers.seller1, em);
        Products.product2 = createProduct("banana", "descriereBanana", "/banana", 3F, Categories.category2, Sellers.seller2, em);
        Products.product3 = createProduct("pizza", "descrierePizza", "/pizza", 8F, Categories.category3, Sellers.seller1, em);
    }

    public static Cart createCart(EntityManager em) {
        Cart cart = new Cart();
        em.persist(cart);
        return cart;
    }

    public static void createCartBaseData(EntityManager em) {
        Carts.cart = createCart(em);
    }

    public static CartItem createCartItem(Product product, float quantity, EntityManager em) {
        Cart cart = Carts.cart;
        CartItem cartItem = cart.addToCart(product, quantity);
        return cartItem;
    }

    public static void createCartItemsBaseData(EntityManager em) {
        CartItems.cartItem1 = createCartItem(Products.product1, 5F, em);
        CartItems.cartItem2 = createCartItem(Products.product2, 10F, em);
    }

    public static Buyer createBuyer(UserAccount userAccount, EntityManager em) {
        Buyer buyer = new Buyer(userAccount);
        em.persist(buyer);
        return buyer;
    }

    public static void createBuyersBaseData(EntityManager em) {
        Buyers.buyers1 = createBuyer(new UserAccount("user", "name", "user@gmail.com", "1234", "imageNameAcc", "0747871208"), em);
        Buyers.buyers2 = createBuyer(new UserAccount("user2", "name", "user2@gmail.com", "1234", "imageNameAcc", "0747871208"), em);

    }

    public static Review createReview(String description, float rating, Buyer buyer, Product product, EntityManager em) {
        Seller seller = product.getSeller();
        Review review = seller.addReview(buyer, description, rating, product);
        return review;
    }

    public static void createReviewsBaseData(EntityManager em) {
        Reviews.review1 = createReview("primul review", 3.5F, Buyers.buyers1, Products.product2, em);
        Reviews.review2 = createReview("al doilea review", 5F, Buyers.buyers1, Products.product1, em);
        Reviews.review3 = createReview("al treilea review", 4F, Buyers.buyers2, Products.product1, em);
    }

    public static class Categories {
        public static Category category1;
        public static Category category2;
        public static Category category3;
    }

    public static class Sellers {
        public static Seller seller1;
        public static Seller seller2;
    }

    public static class Products {
        public static Product product1;
        public static Product product2;
        public static Product product3;
    }

    public static class Carts {
        public static Cart cart;
    }

    public static class CartItems {
        public static CartItem cartItem1;
        public static CartItem cartItem2;
    }

    public static class Buyers {
        public static Buyer buyers1;
        public static Buyer buyers2;
    }

    public static class Reviews {
        public static Review review1;
        public static Review review2;
        public static Review review3;
    }

}
