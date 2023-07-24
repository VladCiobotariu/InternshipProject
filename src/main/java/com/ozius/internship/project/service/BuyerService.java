package com.ozius.internship.project.service;

import com.ozius.internship.project.entity.BuyerInfo;
import com.ozius.internship.project.entity.CartItem;
import com.ozius.internship.project.entity.Product;
import com.ozius.internship.project.repository.BuyerRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Set;

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
    public void createOrdersByCart(BuyerInfo buyer){

        BuyerInfo buyerMerged = em.merge(buyer);

        Set<CartItem> cartItems = buyerMerged.getCart().getCartItems();


    }
}
