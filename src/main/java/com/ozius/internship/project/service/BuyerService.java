package com.ozius.internship.project.service;

import com.ozius.internship.project.entity.buyer.Buyer;
import com.ozius.internship.project.entity.buyer.BuyerAddress;
import com.ozius.internship.project.entity.product.Product;
import com.ozius.internship.project.repository.BuyerRepository;
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
    private final UserAccountRepository userAccountRepository;

    public BuyerService(BuyerRepository buyerRepository, UserAccountRepository userAccountRepository) {
        this.buyerRepository = buyerRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @Transactional
    public Buyer getBuyerByEmail(String email){
        long userId = userAccountRepository.findByEmail(email).getId();
        return buyerRepository.findBuyerByAccount_Id(userId);
    }

    @Transactional
    public Set<Product> getFavoritesByUserEmail(String email) {
        Buyer buyer = getBuyerByEmail(email);
        return buyer.getFavoriteProducts();
    }

    @Transactional
    public void addFavoriteByProductId(String email, long productId) {
        Buyer buyer = getBuyerByEmail(email);
        Product product = em.find(Product.class, productId);
        buyer.addFavorite(product);
    }


    @Transactional
    public void removeFavoriteByProductId(String email, long productId) {
        Buyer buyer = getBuyerByEmail(email);
        //TODO ask, cant implement with paging and sorting repo
        Product product = em.find(Product.class, productId);
        buyer.removeFavorite(product);
    }

    @Transactional
    public Set<BuyerAddress> getBuyerAddressesByUserEmail(String email){
        Buyer buyer = getBuyerByEmail(email);
        return buyer.getAddresses();
    }
}
