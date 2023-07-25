package com.ozius.internship.project.service;

import com.ozius.internship.project.entity.*;
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
    public void addBuyer(BuyerInfo buyer){
        em.persist(buyer);
    }

    @Transactional
    public void addToCart(BuyerInfo buyer, Product product, float quantity){

        BuyerInfo buyerMerged = em.merge(buyer);

        buyerMerged.getCart().addToCart(product, quantity);
    }

    @Transactional
    public CartItem findCartItemByName(BuyerInfo buyer, String name){

        BuyerInfo buyerMerged = em.merge(buyer);

        return buyerMerged.getCart().getCartItems().stream()
                .filter(cartItem -> cartItem.getProduct().getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    @Transactional
    public void addAddress(){

    }

    @Transactional
    public void createOrdersByCart(BuyerInfo buyer, Address address, String telephone){

        BuyerInfo buyerMerged = em.merge(buyer);

        Set<CartItem> cartItems = buyerMerged.getCart().getCartItems();

        Map<SellerInfo, List<CartItem>> cartItemsBySeller = new HashMap<>();

        for (CartItem item : cartItems) {
            SellerInfo sellerInfo = item.getProduct().getSellerInfo();
            cartItemsBySeller.computeIfAbsent(sellerInfo, k -> new ArrayList<>()).add(item);
        }

        for (Map.Entry<SellerInfo, List<CartItem>> entry : cartItemsBySeller.entrySet()) {
            SellerInfo sellerInfo = entry.getKey();
            List<CartItem> sellerCartItems = entry.getValue();

            Order order = new Order(address, buyer, sellerInfo, telephone, sellerCartItems.stream()
                    .map(cartItem -> new OrderItem(cartItem.getProduct(), cartItem.getQuantity()))
                    .collect(Collectors.toSet()));

            em.persist(order);
        }
    }
}
