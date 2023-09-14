package com.ozius.internship.project.service;

import com.ozius.internship.project.entity.Product;
import com.ozius.internship.project.entity.cart.Cart;
import com.ozius.internship.project.repository.BuyerRepository;
import com.ozius.internship.project.repository.CartRepository;
import com.ozius.internship.project.repository.UserAccountRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class BuyerService {

    @PersistenceContext
    private EntityManager em;
    private final BuyerRepository buyerRepository;
    private final CartRepository cartRepository;
    private final UserAccountRepository userAccountRepository;

    public BuyerService(BuyerRepository buyerRepository, CartRepository cartRepository, UserAccountRepository userAccountRepository) {
        this.buyerRepository = buyerRepository;
        this.cartRepository = cartRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @Transactional
    public Cart getCartItemsByUserEmail(String email){
        long userId = userAccountRepository.findByEmail(email).getId();
        long buyerId = buyerRepository.findBuyerByAccount_Id(userId).getId();

        if(cartRepository.findCartByBuyer_Id(buyerId) == null){
            return null;
        }

        return cartRepository.findCartByBuyer_Id(buyerId);
    }

    @Transactional
    public Set<Product> getFavoritesByUserEmail(String email){
        long userId = userAccountRepository.findByEmail(email).getId();

        return buyerRepository.findBuyerByAccount_Id(userId).getFavoriteProducts();
    }

    @Transactional
    public void removeFavoriteByProductId(String email, long productId){
        long userId = userAccountRepository.findByEmail(email).getId();
        Product product = em.find(Product.class, productId);
        buyerRepository.findBuyerByAccount_Id(userId).removeFavorite(product);
    }

    @Transactional
    public void removeCartItemByProductId(String email, long productId){
        long userId = userAccountRepository.findByEmail(email).getId();
        long buyerId = buyerRepository.findBuyerByAccount_Id(userId).getId();

        Product product = em.find(Product.class, productId);

        cartRepository.findCartByBuyer_Id(buyerId).removeFromCart(product);
    }

    @Transactional
    public void updateCartItemByProductId(String email, long productId, float quantity){
        long userId = userAccountRepository.findByEmail(email).getId();
        long buyerId = buyerRepository.findBuyerByAccount_Id(userId).getId();

        Product product = em.find(Product.class, productId);

        cartRepository.findCartByBuyer_Id(buyerId).addOrUpdateItem(product, quantity);
    }
}
