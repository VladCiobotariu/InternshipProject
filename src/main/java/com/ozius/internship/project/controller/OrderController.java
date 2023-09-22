package com.ozius.internship.project.controller;

import com.ozius.internship.project.dto.BuyerAddressDto;
import com.ozius.internship.project.dto.CheckoutItemDto;
import com.ozius.internship.project.service.OrderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order")
    @PreAuthorize("hasRole('CLIENT')")
    public void makeOrder(@RequestParam(value = "address") BuyerAddressDto shippingAddress,
                          @RequestParam(value = "products") List<CheckoutItemDto> products,
                          @RequestParam(value = "email") String email) {

        orderService.makeOrderFromCheckout(email, shippingAddress, products);
    }
}
