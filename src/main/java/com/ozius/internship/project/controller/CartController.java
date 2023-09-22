package com.ozius.internship.project.controller;

import com.ozius.internship.project.dto.CartDTO;
import com.ozius.internship.project.entity.cart.Cart;
import com.ozius.internship.project.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;

@RestController
public class CartController {

    private final CartService cartService;
    private final ModelMapper modelMapper;

    public CartController(CartService cartService, ModelMapper modelMapper) {
        this.cartService = cartService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/my-cart")
    @PreAuthorize("hasRole('CLIENT')")
    public CartDTO retrieveCartByUserEmail(Principal principal) {
        String loggedUserName = principal.getName();

        Cart cart = cartService.getCartByUserEmail(loggedUserName);
        if(cart == null){
            return new CartDTO(0, new HashSet<>(), 0d);
        }
        return modelMapper.map(cart, CartDTO.class);
    }

    @PutMapping("/my-cart")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<String> updateCartItemByProductId(Principal principal,
                                                            @RequestParam(value = "productId") long productId,
                                                            @RequestParam(value = "quantity") float quantity) {

        String loggedUserName = principal.getName();

        cartService.updateCartItemByProductId(loggedUserName, productId, quantity);
        return ResponseEntity.ok("updated cart item");
    }

    @DeleteMapping("/my-cart")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<String> deleteCartItemByProductId(@RequestParam(value = "productId") long productId, Principal principal) {
        String loggedUserName = principal.getName();

        cartService.removeCartItemByProductId(loggedUserName, productId);
        return ResponseEntity.ok("deleted cart item");
    }
}
