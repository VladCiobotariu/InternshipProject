package com.ozius.internship.project.service;

import com.ozius.internship.project.entity.Product;
import com.ozius.internship.project.entity.SellerInfo;
import com.ozius.internship.project.repository.SellerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerService {

    @PersistenceContext
    private EntityManager em;

    private SellerRepository sellerRepository;

    public SellerService(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    @Transactional
    public void addSeller(SellerInfo seller){
        em.persist(seller);
    }

    @Transactional
    public void addProduct(Product product){
        em.persist(product);
    }


}
