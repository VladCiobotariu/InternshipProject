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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    @PersistenceContext
    private EntityManager em;
    private final BuyerService buyerService;

    public OrderService(BuyerService buyerService) {
        this.buyerService = buyerService;
    }

    @Transactional //TODO maybe return object and put in response header
    public void makeOrdersFromCheckout(String buyerEmail, BuyerAddressDto shippingAddress, List<CheckoutItemDto> products) {

        Address address = shippingAddress.getAddress();
        String buyerFirstName = shippingAddress.getFirstName();
        String buyerLastName = shippingAddress.getLastName();
        String buyerTelephone = shippingAddress.getTelephone();

        Buyer buyer = buyerService.getBuyerByEmail(buyerEmail);
        if(buyer==null){
            throw new IllegalArgumentException("buyer doesn't exits");
        }

        Map<Seller, Order> sellersToOrder = new HashMap<>();
        for(CheckoutItemDto checkoutItemDto : products){
            Product product = em.find(Product.class, checkoutItemDto.getProductId());
            if(product == null){
                throw new IllegalArgumentException("product with id: " + checkoutItemDto.getProductId() + " doesn't exits");
            }

            Seller seller = product.getSeller();

            Order orderPersisted = sellersToOrder.computeIfAbsent(seller, k -> {
                Order order = new Order(address, buyer, k, buyerEmail, buyerFirstName, buyerLastName, buyerTelephone);
                em.persist(order);
                return order;
            });
            orderPersisted.addProduct(product, checkoutItemDto.getQuantity());
        }
    }
}
