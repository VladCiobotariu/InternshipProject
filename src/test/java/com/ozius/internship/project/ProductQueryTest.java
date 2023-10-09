package com.ozius.internship.project;

import com.ozius.internship.project.dto.OrderDTO;
import com.ozius.internship.project.dto.ProductDTO;
import com.ozius.internship.project.entity.Address;
import com.ozius.internship.project.entity.Category;
import com.ozius.internship.project.entity.UserAccount;
import com.ozius.internship.project.entity.buyer.Buyer;
import com.ozius.internship.project.entity.order.Order;
import com.ozius.internship.project.entity.order.OrderStatus;
import com.ozius.internship.project.entity.product.UnitOfMeasure;
import com.ozius.internship.project.entity.seller.Seller;
import com.ozius.internship.project.service.queries.OrderPaginationSearchQuery;
import com.ozius.internship.project.service.queries.ProductPaginationSearchQuery;
import com.ozius.internship.project.service.queries.filter.FilterCriteria;
import com.ozius.internship.project.service.queries.filter.FilterSpecs;
import com.ozius.internship.project.service.queries.filter.Operation;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static com.ozius.internship.project.TestDataCreator.*;
import static com.ozius.internship.project.TestDataCreator.Addresses.address1;
import static com.ozius.internship.project.TestDataCreator.Buyers.buyer1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductQueryTest extends JpaBaseEntity {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void createTestData(EntityManager em) {

        Category category1 = createCategory(em, "Fruits", "/images/fruits.svg");
        Category category2 = createCategory(em, "Vegetables", "/images/vegetables.svg");
        Category category3 = createCategory(em, "Dairy", "/images/dairy.svg");

        UserAccount account1 = new UserAccount("Vlad",
                "Ciobotariu",
                "vladciobotariu1@gmail.com",
                "/src/image1",
                "0734896512");
        account1.setInitialPassword(passwordEncoder.encode("Ozius1234!"));
        Seller seller1 = createSellerFarmer(em,
                new Address("Romania",
                        "Timis",
                        "Timisoara",
                        "Strada Circumvalatiunii nr 4",
                        "Bloc 3 Scara B Ap 12",
                        "303413"),
                account1,
                "Mega Fresh SRL"
        );

        UserAccount account2 = new UserAccount("Mihnea",
                "Mondialu",
                "mihneamondialu@gmail.com",
                "/src/image99",
                "0734896777");
        account2.setInitialPassword(passwordEncoder.encode("Ozius1234!"));
        Seller seller2 = createSellerFarmer(em,
                new Address("Spania",
                        "Granada",
                        "Barcelona",
                        "Strada Real Madrid nr 4",
                        "Bloc Cupa Romaniei",
                        "307773"),
                account2,
                "FC BARCELONA"
        );

        createProduct(em, "Apple", "This is an apple! It is a fruit!", "/images/apple.jpeg", 12.7f, category1, seller1, UnitOfMeasure.KILOGRAM);
        createProduct(em, "Pear", "This is a pear! It is a fruit!", "/images/pear.png", 8.2f, category2, seller2, UnitOfMeasure.KILOGRAM);
        createProduct(em, "Kiwi", "This is a kiwi! It is a fruit!", "mages/kiwi.jpg", 4f, category1, seller1, UnitOfMeasure.KILOGRAM);
        createProduct(em, "Banana", "This is a banana! It is a fruit!", "/images/banana.jpg", 9.8f, category3, seller2, UnitOfMeasure.KILOGRAM);
        createProduct(em, "Mango", "This is a mango! It is a fruit!", "/images/mango.png", 2f, category2, seller1, UnitOfMeasure.KILOGRAM);
    }

    //TODO ask about exceptions thrown when putting instead of number a string and vice versa
    @Test
    void filterSpec_cityName_startsWith_string(){

        //---Act
        List<ProductDTO> products = doTransaction(em -> {

            FilterCriteria filterCriteria = new FilterCriteria("cityName", Operation.STARTS_WITH, "Timisoara");
            FilterSpecs filterSpecs = new FilterSpecs();
            filterSpecs.addFilterCriteria(filterCriteria);

            return new ProductPaginationSearchQuery(modelMapper,em)
                    .filterBy(filterSpecs)
                    .getResultList();
        });

        //---Assert
        assertThat(products.size()).isEqualTo(3);
        assertTrue(products.stream().anyMatch(item->item.getName().equals("Kiwi")));
        assertTrue(products.stream().anyMatch(item->item.getName().equals("Apple")));
        assertTrue(products.stream().anyMatch(item->item.getName().equals("Mango")));
    }

    @Test
    void filterSpec_productPrice_equals_number(){

        //---Act
        List<ProductDTO> products = doTransaction(em -> {

            FilterCriteria filterCriteria = new FilterCriteria("productPrice", Operation.EQ, "2");
            FilterSpecs filterSpecs = new FilterSpecs();
            filterSpecs.addFilterCriteria(filterCriteria);

            return new ProductPaginationSearchQuery(modelMapper,em)
                    .filterBy(filterSpecs)
                    .getResultList();
        });

        //---Assert
        assertThat(products.size()).isEqualTo(1);
        assertTrue(products.stream().anyMatch(item->item.getName().equals("Mango")));
    }

    @Test
    void filterSpec_productPrice_greaterThan_number(){

        //---Act
        List<ProductDTO> products = doTransaction(em -> {

            FilterCriteria filterCriteria = new FilterCriteria("productPrice", Operation.GT, "12");
            FilterSpecs filterSpecs = new FilterSpecs();
            filterSpecs.addFilterCriteria(filterCriteria);

            return new ProductPaginationSearchQuery(modelMapper,em)
                    .filterBy(filterSpecs)
                    .getResultList();
        });

        //---Assert
        assertThat(products.size()).isEqualTo(1);
        assertTrue(products.stream().anyMatch(item->item.getName().equals("Apple")));
    }

    @Test
    void filterSpec_productPrice_lessThan_number(){

        //---Act
        List<ProductDTO> products = doTransaction(em -> {

            FilterCriteria filterCriteria = new FilterCriteria("productPrice", Operation.LT, "3");
            FilterSpecs filterSpecs = new FilterSpecs();
            filterSpecs.addFilterCriteria(filterCriteria);

            return new ProductPaginationSearchQuery(modelMapper,em)
                    .filterBy(filterSpecs)
                    .getResultList();
        });

        //---Assert
        assertThat(products.size()).isEqualTo(1);
        assertTrue(products.stream().anyMatch(item->item.getName().equals("Mango")));
    }

    @Test
    void filterSpec_productPrice_greaterThanEquals_float(){

        //---Act
        List<ProductDTO> products = doTransaction(em -> {

            FilterCriteria filterCriteria = new FilterCriteria("productPrice", Operation.GTE, "9.8");
            FilterSpecs filterSpecs = new FilterSpecs();
            filterSpecs.addFilterCriteria(filterCriteria);

            return new ProductPaginationSearchQuery(modelMapper,em)
                    .filterBy(filterSpecs)
                    .getResultList();
        });

        //---Assert
        assertThat(products.size()).isEqualTo(2);
        assertTrue(products.stream().anyMatch(item->item.getName().equals("Apple")));
        assertTrue(products.stream().anyMatch(item->item.getName().equals("Banana")));
    }

    @Test
    void filterSpec_productPrice_lessThanEquals_number(){

        //---Act
        List<ProductDTO> products = doTransaction(em -> {

            FilterCriteria filterCriteria = new FilterCriteria("productPrice", Operation.LTE, "4");
            FilterSpecs filterSpecs = new FilterSpecs();
            filterSpecs.addFilterCriteria(filterCriteria);

            return new ProductPaginationSearchQuery(modelMapper,em)
                    .filterBy(filterSpecs)
                    .getResultList();
        });

        //---Assert
        assertThat(products.size()).isEqualTo(2);
        assertTrue(products.stream().anyMatch(item->item.getName().equals("Mango")));
        assertTrue(products.stream().anyMatch(item->item.getName().equals("Kiwi")));
    }

    @Test
    void filterSpec_productName_startsWith_string(){

        //---Act
        List<ProductDTO> products = doTransaction(em -> {

            FilterCriteria filterCriteria = new FilterCriteria("productName", Operation.STARTS_WITH, "App");
            FilterSpecs filterSpecs = new FilterSpecs();
            filterSpecs.addFilterCriteria(filterCriteria);

            return new ProductPaginationSearchQuery(modelMapper,em)
                    .filterBy(filterSpecs)
                    .getResultList();
        });

        //---Assert
        assertThat(products.size()).isEqualTo(1);
        assertTrue(products.stream().anyMatch(item->item.getName().equals("Apple")));
    }

    @Test
    void filterSpec_productName_contains_string(){

        //---Act
        List<ProductDTO> products = doTransaction(em -> {

            FilterCriteria filterCriteria = new FilterCriteria("productName", Operation.CONTAINS, "an");
            FilterSpecs filterSpecs = new FilterSpecs();
            filterSpecs.addFilterCriteria(filterCriteria);

            return new ProductPaginationSearchQuery(modelMapper,em)
                    .filterBy(filterSpecs)
                    .getResultList();
        });

        //---Assert
        assertThat(products.size()).isEqualTo(2);
        assertTrue(products.stream().anyMatch(item->item.getName().equals("Mango")));
        assertTrue(products.stream().anyMatch(item->item.getName().equals("Banana")));
    }

    @Test
    void filterSpec_productName_endsWith_string(){

        //---Act
        List<ProductDTO> products = doTransaction(em -> {

            FilterCriteria filterCriteria = new FilterCriteria("productName", Operation.ENDS_WITH, "wi");
            FilterSpecs filterSpecs = new FilterSpecs();
            filterSpecs.addFilterCriteria(filterCriteria);

            return new ProductPaginationSearchQuery(modelMapper,em)
                    .filterBy(filterSpecs)
                    .getResultList();
        });

        //---Assert
        assertThat(products.size()).isEqualTo(1);
        assertTrue(products.stream().anyMatch(item->item.getName().equals("Kiwi")));
    }

    @Test
    void filterSpec_orderStatus_draft(){

        //---Arrange
        doTransaction(em -> {
            TestDataCreator.createBuyerBaseData(em, passwordEncoder);
            TestDataCreator.createAddresses();

            Buyer buyerMerged = em.merge(buyer1);
            Seller sellerMerged = new EntityFinder(em).findAll(Seller.class).get(0);

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

            FilterCriteria filterCriteria = new FilterCriteria("orderStatus", Operation.EQ, OrderStatus.DRAFT);
            FilterSpecs filterSpecs = new FilterSpecs();
            filterSpecs.addFilterCriteria(filterCriteria);

            return new OrderPaginationSearchQuery(modelMapper,em)
                    .filterBy(filterSpecs)
                    .getResultList();
        });

        //---Assert
        assertThat(orders.size()).isEqualTo(1);
    }

    @Test
    void filterSpec_orderStatus_submit(){

        //---Arrange
        doTransaction(em -> {
            TestDataCreator.createBuyerBaseData(em, passwordEncoder);
            TestDataCreator.createAddresses();

            Buyer buyerMerged = em.merge(buyer1);
            Seller sellerMerged = new EntityFinder(em).findAll(Seller.class).get(0);

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
