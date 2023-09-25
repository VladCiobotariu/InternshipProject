package com.ozius.internship.project.controller;

import com.ozius.internship.project.dto.CartDTO;
import com.ozius.internship.project.dto.CartItemDTO;
import com.ozius.internship.project.entity.cart.Cart;
import com.ozius.internship.project.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.Comparator;

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
    public ResponseEntity<Object> retrieveCartByUserEmail(Principal principal) {
        String loggedUserName = principal.getName();

        Cart cart = cartService.getCartByUserEmail(loggedUserName);
        if(cart == null){
           return ResponseEntity.notFound().build();
        }
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        cartDTO.getCartItems().sort(Comparator.comparingLong(CartItemDTO::getId));

        return ResponseEntity.ok(cartDTO);
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
