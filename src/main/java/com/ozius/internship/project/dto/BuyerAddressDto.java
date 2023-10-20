package com.ozius.internship.project.dto;

import com.ozius.internship.project.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyerAddressDto {
    private long id;
    private Address address;
    private String firstName;
    private String lastName;
    private String telephone;
}
