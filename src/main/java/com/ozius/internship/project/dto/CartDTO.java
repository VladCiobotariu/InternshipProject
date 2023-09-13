package com.ozius.internship.project.dto;

import lombok.Data;

import java.util.Set;

@Data
public class CartDTO {
    private long id;
    private Set<CartItemDTO> cartItems;
    private double totalCartPrice;
}
