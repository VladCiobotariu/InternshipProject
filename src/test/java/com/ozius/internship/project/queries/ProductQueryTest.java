package com.ozius.internship.project.queries;

import com.ozius.internship.project.dto.ProductDTO;
import com.ozius.internship.project.entity.Address;
import com.ozius.internship.project.entity.Category;
import com.ozius.internship.project.entity.UserAccount;
import com.ozius.internship.project.entity.seller.Seller;
import com.ozius.internship.project.service.queries.ProductPaginationSearchQuery;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static com.ozius.internship.project.TestDataCreator.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductQueryTest extends QueryBaseTest{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    void test_jpa_category_query_test(){

        //---Arrange
        doTransaction(em -> {
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

            createProduct(em, "Apple", "This is an apple! It is a fruit!", "/images/apple.jpeg", 12.7f, category1, seller1);
            createProduct(em, "Pear", "This is a pear! It is a fruit!", "/images/pear.png", 8.2f, category2, seller2);
            createProduct(em, "Kiwi", "This is a kiwi! It is a fruit!", "mages/kiwi.jpg", 5f, category1, seller1);
            createProduct(em, "Banana", "This is a banana! It is a fruit!", "/images/banana.jpg", 5f, category3, seller2);
            createProduct(em, "Mango", "This is a mango! It is a fruit!", "/images/mango.png", 5f, category2, seller1);
        });

        //---Act
        List<ProductDTO> products = doTransaction(em -> {
            return new ProductPaginationSearchQuery(modelMapper,em)
                    .withCategory("Fruits")
                    .getResultList();
        });

        //---Assert
        assertThat(products.size()).isEqualTo(2);
        assertTrue(products.stream().anyMatch(item->item.getName().equals("Kiwi")));
        assertTrue(products.stream().anyMatch(item->item.getName().equals("Apple")));
    }
}
