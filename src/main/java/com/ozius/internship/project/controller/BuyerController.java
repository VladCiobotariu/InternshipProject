package com.ozius.internship.project.controller;

import com.ozius.internship.project.dto.BuyerAddressDto;
import com.ozius.internship.project.dto.ProductDTO;
import com.ozius.internship.project.entity.buyer.BuyerAddress;
import com.ozius.internship.project.entity.product.Product;
import com.ozius.internship.project.service.BuyerService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;
import java.util.stream.Stream;

@RestController
public class BuyerController {

    private final BuyerService buyerService;
    private final ModelMapper modelMapper;
    private Set<Product> favorites;

    public BuyerController(BuyerService buyerService, ModelMapper modelMapper) {
        this.buyerService = buyerService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/my-favorites")
    @PreAuthorize("hasRole('CLIENT')")
    public Stream<ProductDTO> retrieveFavoritesByUserEmail(Principal principal) {
        String loggedUserName = principal.getName();

        Set<Product> favorites = buyerService.getFavoritesByUserEmail(loggedUserName);
        return favorites.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class));
    }

    @PutMapping("/my-favorites")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<String> addFavoriteByProductId(Principal principal, @RequestParam(value = "productId") long productId) {
        String loggedUsername = principal.getName();

        buyerService.addFavoriteByProductId(loggedUsername, productId);
        return ResponseEntity.ok("added favorite product");

    }

    @DeleteMapping("/my-favorites")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<String> deleteFavoriteByProductId(@RequestParam(value = "productId") long productId, Principal principal) {
        String loggedUserName = principal.getName();

        buyerService.removeFavoriteByProductId(loggedUserName, productId);
        return ResponseEntity.ok("favorite deleted");
    }

    @GetMapping("/my-buyer-addresses")
    public Stream<BuyerAddressDto> retrieveBuyerAddressesByUserEmail(Principal principal){
        String loggedUserName = principal.getName();

        Set<BuyerAddress> buyerAddresses = buyerService.getBuyerAddressesByUserEmail(loggedUserName);
        return buyerAddresses.stream()
                .map(address -> modelMapper.map(address, BuyerAddressDto.class));
    }

}
