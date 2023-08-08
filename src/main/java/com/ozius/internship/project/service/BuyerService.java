package com.ozius.internship.project.service;

import com.ozius.internship.project.entity.*;
import com.ozius.internship.project.entity.cart.CartItem;
import com.ozius.internship.project.entity.order.Order;
import com.ozius.internship.project.entity.order.OrderItem;
import com.ozius.internship.project.entity.seller.Seller;
import com.ozius.internship.project.repository.BuyerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BuyerService {

    @PersistenceContext
    private EntityManager em;

    private BuyerRepository buyerRepository;

    public BuyerService(BuyerRepository buyerRepository) {
        this.buyerRepository = buyerRepository;
    }

    @Transactional
    public void addBuyer(Buyer buyer){
        em.persist(buyer);
    }

    @Transactional
    public void addToCart(Buyer buyer, Product product, float quantity){

        Buyer buyerMerged = em.merge(buyer);

        buyerMerged.getCart().addToCart(product, quantity);
    }

    @Transactional
    public CartItem findCartItemByName(Buyer buyer, String name){

        Buyer buyerMerged = em.merge(buyer);

        return buyerMerged.getCart().getCartItems().stream()
                .filter(cartItem -> cartItem.getProduct().getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    @Transactional
    public void addAddress(){

    }

    @Transactional
    public void createOrdersByCart(Buyer buyer, Address address, String telephone){

        Buyer buyerMerged = em.merge(buyer);

        Set<CartItem> cartItems = buyerMerged.getCart().getCartItems();

        Map<Seller, List<CartItem>> cartItemsBySeller = new HashMap<>();

        for (CartItem item : cartItems) {
            Seller seller = item.getProduct().getSeller();
            cartItemsBySeller.computeIfAbsent(seller, k -> new ArrayList<>()).add(item);
        }

        for (Map.Entry<Seller, List<CartItem>> entry : cartItemsBySeller.entrySet()) {
            Seller seller = entry.getKey();
            List<CartItem> sellerCartItems = entry.getValue();

            Order order = new Order(address, buyer, seller, telephone, sellerCartItems.stream()
                    .map(cartItem -> new OrderItem(cartItem.getProduct(), cartItem.getQuantity()))
                    .collect(Collectors.toSet()));

            em.persist(order);
        }
    }
}
