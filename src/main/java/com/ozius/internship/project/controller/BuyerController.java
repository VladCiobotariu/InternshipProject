package com.ozius.internship.project.controller;

import com.ozius.internship.project.dto.CartDTO;
import com.ozius.internship.project.dto.ProductDTO;
import com.ozius.internship.project.service.BuyerService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.stream.Stream;

@RestController
public class BuyerController {

    private final BuyerService buyerService;
    private final ModelMapper modelMapper;

    public BuyerController(BuyerService buyerService, ModelMapper modelMapper) {
        this.buyerService = buyerService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/my-cart")
    @PreAuthorize("hasRole('CLIENT')")
    public CartDTO retrieveCartItemsByUserEmail(Principal principal){
        String loggedUserName = principal.getName();

        return modelMapper.map(buyerService.getCartItemsByUserEmail(loggedUserName), CartDTO.class);
    }

    @DeleteMapping("/my-cart")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<String> deleteCartItemByProductId(@RequestParam(value = "productId") long productId, Principal principal){
        String loggedUserName = principal.getName();
        buyerService.removeCartItemByProductId(loggedUserName, productId);

        return ResponseEntity.ok("deleted cart item");
    }

    @PutMapping("/my-cart")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<String> updateCartItemByProductId(Principal principal,
            @RequestParam(value = "productId") long productId,
            @RequestParam(value = "quantity") float quantity){

        String loggedUserName = principal.getName();
        buyerService.updateCartItemByProductId(loggedUserName, productId, quantity);

        return ResponseEntity.ok("deleted cart item");
    }

    @GetMapping("/my-favorites")
    @PreAuthorize("hasRole('CLIENT')")
    public Stream<ProductDTO> retrieveFavoritesByUserEmail(Principal principal){
        String loggedUserName = principal.getName();

        return buyerService.getFavoritesByUserEmail(loggedUserName).stream().map(product -> modelMapper.map(product, ProductDTO.class));
    }

    @DeleteMapping("/my-favorites")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<String> deleteFavoriteByProductId(@RequestParam(value = "productId") long productId, Principal principal){
        String loggedUserName = principal.getName();

        buyerService.removeFavoriteByProductId(loggedUserName, productId);
        return ResponseEntity.ok().body("favorite deleted");
    }

}
