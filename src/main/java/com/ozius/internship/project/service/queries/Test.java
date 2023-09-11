package com.ozius.internship.project.service.queries;

import com.ozius.internship.project.entity.Product;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

public class Test {

    private EntityManager em;

    public void search(){
        //entity
        List<Product> products = em.createQuery("select p from Product p", Product.class).getResultList();

        //array
        List<Object[]> rows = em.createQuery("select p.name, p.description from Product p", Object[].class).getResultList();

        List<ProductBaseDto> productBaseDtos = new ArrayList<>();

        for(Object[] row : rows){
            String name = (String) row[0];
            String description = (String) row[1];
            productBaseDtos.add(new ProductBaseDto(name, description));
        }

        //jpa
        List<ProductBaseDto> productBaseDtos1 = em
                .createQuery("select new com.ozius.internship.project.service.queries.ProductBaseDto(p.name, p.description) from Product p",
                        ProductBaseDto.class).getResultList();
    }
}
