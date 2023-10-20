package com.ozius.internship.project.service.queries;

import com.ozius.internship.project.dto.OrderDTO;
import com.ozius.internship.project.entity.order.Order;
import com.ozius.internship.project.service.queries.buiders.PagingJpaQueryBuilder;
import com.ozius.internship.project.service.queries.filter.FilterSpecs;
import com.ozius.internship.project.service.queries.sort.SortSpecs;
import com.ozius.internship.project.service.queries.transformers.ModelMapperBasedResultTransformer;
import com.ozius.internship.project.service.queries.transformers.ResultTransformer;
import jakarta.persistence.EntityManager;
import org.modelmapper.ModelMapper;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

public class OrderPaginationSearchQuery extends PagingJpaQueryBuilder<Order, OrderDTO> {

    private final ModelMapper modelMapper;
    public OrderPaginationSearchQuery(ModelMapper modelMapper, EntityManager em) {
        super("select o from Order o ", em, Order.class);
        this.modelMapper = modelMapper;

        mapCriteriaToPropertyPath("orderStatus", "o.orderStatus");
    }

    @Override
    public String orderByDefault() {
        return "o.id";
    }

    @Override
    public ResultTransformer<Order, OrderDTO> getTransformer() {
        return new ModelMapperBasedResultTransformer<>(modelMapper, OrderDTO.class);
    }

    public OrderPaginationSearchQuery orderBy(SortSpecs sortSpecs) {
        if(isNotEmpty(sortSpecs)) {
            applySortSpecs(sortSpecs);
        }
        return this;
    }

    public OrderPaginationSearchQuery filterBy(FilterSpecs filterSpecs) {
        if(isNotEmpty(filterSpecs)) {
            applyFilterSpecs(filterSpecs);
        }
        return this;
    }

}
