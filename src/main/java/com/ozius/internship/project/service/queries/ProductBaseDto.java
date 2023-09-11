package com.ozius.internship.project.service.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductBaseDto {
    private String name;
    private String description;
}
