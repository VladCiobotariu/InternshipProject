package com.ozius.internship.project.dto;

import com.ozius.internship.project.entity.Address;
import lombok.Data;

@Data
public class BuyerAddressDto {
    private Address address;
    private String firstName;
    private String lastName;
    private String telephone;
}
