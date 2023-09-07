package com.ozius.internship.project.service;

import com.ozius.internship.project.entity.Product;
import com.ozius.internship.project.entity.cart.CartItem;
import com.ozius.internship.project.repository.BuyerRepository;
import com.ozius.internship.project.repository.CartRepository;
import com.ozius.internship.project.repository.UserAccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class BuyerService {

    private final BuyerRepository buyerRepository;
    private final CartRepository cartRepository;
    private final UserAccountRepository userAccountRepository;

    public BuyerService(BuyerRepository buyerRepository, CartRepository cartRepository, UserAccountRepository userAccountRepository) {
        this.buyerRepository = buyerRepository;
        this.cartRepository = cartRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @Transactional
    public Set<CartItem> getCartItemsByUserEmail(String email){
        long userId = userAccountRepository.findByEmail(email).getId();
        long buyerId = buyerRepository.findBuyerByAccount_Id(userId).getId();

        if(cartRepository.findCartByBuyer_Id(buyerId) == null){
            return null;
        }

        return cartRepository.findCartByBuyer_Id(buyerId).getCartItems();
    }

    @Transactional
    public Set<Product> getFavoritesByUserEmail(String email){
        long userId = userAccountRepository.findByEmail(email).getId();

        return buyerRepository.findBuyerByAccount_Id(userId).getFavoriteProducts();
    }
}
