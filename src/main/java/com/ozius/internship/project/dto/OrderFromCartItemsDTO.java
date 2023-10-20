package com.ozius.internship.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderFromCartItemsDTO {
    private BuyerAddressDto shippingAddress;
    private List<CheckoutItemDto> products;
    private String email;
}
