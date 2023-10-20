package com.ozius.internship.project.controller;

import com.ozius.internship.project.entity.seller.Seller;
import com.ozius.internship.project.repository.SellerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class LocationController {

    private final SellerRepository sellerRepository;

    public LocationController(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    @GetMapping("/cities")
    public ResponseEntity<List<String>> getListOfLocations() {
        List<Seller> sellers = sellerRepository.findAll();
        List<String> cities = sellers.stream()
                .map(seller -> seller.getCity())
                .distinct()
                .collect(Collectors.toList());

        return ResponseEntity.ok(cities);
    }
}
