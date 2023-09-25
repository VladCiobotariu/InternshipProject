package com.ozius.internship.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO{
    private long id;
    private ProductDTO product;
    private float quantity;
}
