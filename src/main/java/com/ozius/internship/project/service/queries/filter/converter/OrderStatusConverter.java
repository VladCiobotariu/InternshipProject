package com.ozius.internship.project.service.queries.filter.converter;

import com.ozius.internship.project.entity.order.OrderStatus;

public class OrderStatusConverter implements FilterValueConverter<OrderStatus>{
    @Override
    public OrderStatus convert(String value) {
        return OrderStatus.valueOf(value.toUpperCase());
    }
}
