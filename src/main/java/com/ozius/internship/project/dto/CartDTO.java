package com.ozius.internship.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private long id;
    private List<CartItemDTO> cartItems;
    private double totalCartPrice;
}
