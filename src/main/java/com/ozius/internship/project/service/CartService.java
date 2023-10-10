package com.ozius.internship.project.service;

import com.ozius.internship.project.domain.cart.Cart;
import com.ozius.internship.project.domain.product.Product;
import com.ozius.internship.project.repository.CartRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @PersistenceContext
    private EntityManager em;
    private final CartRepository cartRepository;
    private final BuyerService buyerService;

    public CartService(CartRepository cartRepository, BuyerService buyerService) {
        this.cartRepository = cartRepository;
        this.buyerService = buyerService;
    }

    @Transactional
    public Cart getCartByUserEmail(String email) {

        long buyerId = buyerService.getBuyerByEmail(email).getId();
        if (cartRepository.findCartByBuyer_Id(buyerId) == null) {
            return null;
        }

        return cartRepository.findCartByBuyer_Id(buyerId);
    }

    @Transactional
    public void updateCartItemByProductId(String email, long productId, float quantity) {

        long buyerId = buyerService.getBuyerByEmail(email).getId();
        Product product = em.find(Product.class, productId);

        Cart buyerCart = cartRepository.findCartByBuyer_Id(buyerId);
        buyerCart.addOrUpdateItem(product, quantity);
    }

    @Transactional
    public void removeCartItemByProductId(String email, long productId) {
        long buyerId = buyerService.getBuyerByEmail(email).getId();

        Product product = em.find(Product.class, productId);

        Cart buyerCart = cartRepository.findCartByBuyer_Id(buyerId);
        buyerCart.removeFromCart(product);
    }
}
