package com.ozius.internship.project.controller;

import com.ozius.internship.project.entity.cart.CartItem;
import com.ozius.internship.project.repository.CartRepository;
import com.ozius.internship.project.service.BuyerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class BuyerController {

    private final BuyerService buyerService;

    public BuyerController(BuyerService buyerService) {
        this.buyerService = buyerService;
    }

    @GetMapping("/users/{email}/cart")
    @PreAuthorize("#email == authentication.name")
    public Set<CartItem> retrieveCartItemsByUserEmail(@PathVariable String email){
        return buyerService.getCartItemsByUserEmail(email);
    }

}
