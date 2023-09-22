package com.ozius.internship.project.service;

import com.ozius.internship.project.dto.BuyerAddressDto;
import com.ozius.internship.project.dto.CheckoutItemDto;
import com.ozius.internship.project.entity.Address;
import com.ozius.internship.project.entity.buyer.Buyer;
import com.ozius.internship.project.entity.order.Order;
import com.ozius.internship.project.entity.product.Product;
import com.ozius.internship.project.entity.seller.Seller;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class OrderService {

    @PersistenceContext
    private EntityManager em;
    private final BuyerService buyerService;

    public OrderService(BuyerService buyerService) {
        this.buyerService = buyerService;
    }

    @Transactional
    public void makeOrderFromCheckout(String buyerEmail, BuyerAddressDto shippingAddress, List<CheckoutItemDto> products) {

        Address address = shippingAddress.getAddress();
        String buyerFirstName = shippingAddress.getFirstName();
        String buyerLastName = shippingAddress.getLastName();
        String buyerTelephone = shippingAddress.getTelephone();

        Buyer buyer = buyerService.getBuyerByEmail(buyerEmail);
        if(buyer==null){
            throw new IllegalArgumentException("buyer doesn't exits");
        }

        Map<Seller, List<Order>> sellersToOrder = new HashMap<>();
        for(CheckoutItemDto checkoutItemDto : products){
            Product product = em.find(Product.class, checkoutItemDto.getProductId());
            if(product == null){
                throw new IllegalArgumentException("product with id: " + checkoutItemDto.getProductId() + " doesn't exits");
            }

            Seller seller = product.getSeller();
            AtomicReference<Order> order = new AtomicReference<>();
            sellersToOrder.computeIfAbsent(seller, value -> {
                order.set(new Order(address, buyer, seller, buyerEmail, buyerFirstName, buyerLastName, buyerTelephone));
                em.persist(order.get());
                return new ArrayList<>();
            }).add(order.get());
        }
    }
}
