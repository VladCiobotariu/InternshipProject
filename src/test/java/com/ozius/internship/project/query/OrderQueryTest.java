package com.ozius.internship.project.query;

import com.ozius.internship.project.JpaBaseEntity;
import com.ozius.internship.project.TestDataCreator;
import com.ozius.internship.project.dto.OrderDTO;
import com.ozius.internship.project.entity.buyer.Buyer;
import com.ozius.internship.project.entity.order.Order;
import com.ozius.internship.project.entity.order.OrderStatus;
import com.ozius.internship.project.entity.seller.Seller;
import com.ozius.internship.project.service.queries.OrderPaginationSearchQuery;
import com.ozius.internship.project.service.queries.filter.FilterCriteria;
import com.ozius.internship.project.service.queries.filter.FilterSpecs;
import com.ozius.internship.project.service.queries.filter.Operation;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static com.ozius.internship.project.TestDataCreator.Addresses.address1;
import static com.ozius.internship.project.TestDataCreator.Buyers.buyer1;
import static com.ozius.internship.project.TestDataCreator.Sellers.seller1;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderQueryTest extends JpaBaseEntity {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void createTestData(EntityManager em) {
        TestDataCreator.createBuyerBaseData(em, passwordEncoder);
        TestDataCreator.createSellerBaseData(em, passwordEncoder);
        TestDataCreator.createAddresses();
    }

    @Test
    void filterSpec_orderStatus_submit(){

        //---Arrange
        doTransaction(em -> {

            Buyer buyerMerged = em.merge(buyer1);
            Seller sellerMerged = em.merge(seller1);

            Order order = new Order(address1,
                    buyerMerged,
                    sellerMerged,
                    buyerMerged.getAccount().getEmail(),
                    buyerMerged.getAccount().getFirstName(),
                    buyerMerged.getAccount().getLastName(),
                    buyerMerged.getAccount().getTelephone());
            em.persist(order);
        });

        //---Act
        List<OrderDTO> orders = doTransaction(em -> {

            FilterCriteria filterCriteria = new FilterCriteria("orderStatus", Operation.EQ, OrderStatus.SUBMITTED);
            FilterSpecs filterSpecs = new FilterSpecs();
            filterSpecs.addFilterCriteria(filterCriteria);

            return new OrderPaginationSearchQuery(modelMapper,em)
                    .filterBy(filterSpecs)
                    .getResultList();
        });

        //---Assert
        assertThat(orders.size()).isEqualTo(0);
    }
}
