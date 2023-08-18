package com.ozius.internship.project.entity;

import com.ozius.internship.project.TestDataCreator;
import com.ozius.internship.project.entity.buyer.Buyer;
import com.ozius.internship.project.entity.buyer.BuyerAddress;
import com.ozius.internship.project.entity.exeption.IllegalAddressException;
import com.ozius.internship.project.entity.exeption.IllegalItemException;
import com.ozius.internship.project.entity.seller.Seller;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.util.List;

import static com.ozius.internship.project.TestDataCreator.Categories.category1;
import static com.ozius.internship.project.TestDataCreator.Products.product1;
import static com.ozius.internship.project.TestDataCreator.Products.product2;
import static com.ozius.internship.project.TestDataCreator.Sellers.seller1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BuyerEntityTests extends EntityBaseTest{

    private JpaRepository<Buyer, Long> buyerRepository;

    @Override
    public void createTestData(EntityManager em) {
        this.buyerRepository = new SimpleJpaRepository<>(Buyer.class, emb);
    }

    @Test
    void test_add_buyer(){
        //----Act
        doTransaction(em -> {
            Buyer buyer = new Buyer(new UserAccount("Cosmina", "Maria", "cosminamaria@gmail.com", "ozius1223423345", "/src/image2", "0735897635"));
            em.persist(buyer);
        });

        //----Assert
        Buyer persistedBuyer = buyerRepository.findAll().get(0);
        assertThat(persistedBuyer.getAccount().getFirstName()).isEqualTo("Cosmina");
        assertThat(persistedBuyer.getAccount().getLastName()).isEqualTo("Maria");
        assertThat(persistedBuyer.getAccount().getEmail()).isEqualTo("cosminamaria@gmail.com");
        assertThat(persistedBuyer.getAccount().getPasswordHash()).isEqualTo("ozius1223423345");
        assertThat(persistedBuyer.getAccount().getImageName()).isEqualTo("/src/image2");
        assertThat(persistedBuyer.getAccount().getTelephone()).isEqualTo("0735897635");
        assertThat(persistedBuyer.getFavoriteProducts()).isEmpty();
    }

    @Test
    void test_add_address(){

        //----Arrange
        Buyer buyer = doTransaction(em -> {
            UserAccount account = new UserAccount("Cosmina", "Maria", "cosminamaria@gmail.com", "ozius1223423345", "/src/image2", "0735897635");
            return TestDataCreator.createBuyer(em, account);
        });

        //----Act
       doTransaction(em -> {
            Address address = new Address("Romania", "Timis", "Timisoara", "Strada Macilor 10", "Bloc 4, Scara F, ap 50", "300091");
            Buyer mergedBuyer = em.merge(buyer);
            mergedBuyer.addAddress(address);
        });

        //----Assert
        Buyer persistedBuyer = entityFinder.getTheOne(Buyer.class);

        Address persistedAddress = persistedBuyer.getAddresses().stream().findFirst().orElseThrow().getAddress();

        assertThat(persistedAddress.getCountry()).isEqualTo("Romania");
        assertThat(persistedAddress.getState()).isEqualTo("Timis");
        assertThat(persistedAddress.getCity()).isEqualTo("Timisoara");
        assertThat(persistedAddress.getAddressLine1()).isEqualTo("Strada Macilor 10");
        assertThat(persistedAddress.getAddressLine2()).isEqualTo("Bloc 4, Scara F, ap 50");
        assertThat(persistedAddress.getZipCode()).isEqualTo("300091");
    }

    @Test
    void test_add_double_address(){

        //----Arrange
        Buyer buyer = doTransaction(em -> {
            UserAccount account = new UserAccount("Cosmina", "Maria", "cosminamaria@gmail.com", "ozius1223423345", "/src/image2", "0735897635");
            return TestDataCreator.createBuyer(em, account);
        });

        //----Act
        Exception exception = doTransaction(em -> {
            Address address = new Address("Romania", "Timis", "Timisoara", "Strada Macilor 10", "Bloc 4, Scara F, ap 50", "300091");
            Buyer mergedBuyer = em.merge(buyer);

            mergedBuyer.addAddress(address);
            em.flush();

            return assertThrows(IllegalAddressException.class, ()-> mergedBuyer.addAddress(address));
        });

        //----Assert
        assertTrue(exception.getMessage().contains("this address already exists"));
    }

    @Test
    void test_update_buyer_address(){

        //----Arrange
        Buyer buyer = doTransaction(em -> {
            UserAccount account = new UserAccount("Cosmina", "Maria", "cosminamaria@gmail.com", "ozius1223423345", "/src/image2", "0735897635");
            Address address = new Address("Romania", "Timis", "Timisoara", "Strada Macilor 10", "Bloc 4, Scara F, ap 50", "300091");

            Buyer buyerToAdd = TestDataCreator.createBuyer(em, account);
            buyerToAdd.addAddress(address);

            return buyerToAdd;
        });

        //----Act
        doTransaction(em -> {
            Buyer mergedBuyer = em.merge(buyer);

            long addressId = mergedBuyer.getAddresses().stream().findFirst().orElseThrow().getId();
            Address address = new Address("Spain", "Timis", "Timisoara", "Strada Macilor 10", "Bloc 4, Scara F, ap 50", "300091");

            mergedBuyer.updateAddress(address, addressId);
        });

        //----Assert
        Address persistedAddress = entityFinder.getTheOne(Buyer.class).getAddresses().stream().findFirst().orElseThrow().getAddress();

        assertThat(persistedAddress.getCountry()).isEqualTo("Spain");
        assertThat(persistedAddress.getState()).isEqualTo("Timis");
        assertThat(persistedAddress.getCity()).isEqualTo("Timisoara");
        assertThat(persistedAddress.getAddressLine1()).isEqualTo("Strada Macilor 10");
        assertThat(persistedAddress.getAddressLine2()).isEqualTo("Bloc 4, Scara F, ap 50");
        assertThat(persistedAddress.getZipCode()).isEqualTo("300091");
    }

    @Test
    void test_buyer_updated(){

        //----Arrange
        Buyer buyer = doTransaction(em -> {
            UserAccount account = new UserAccount("Cosmina", "Maria", "cosminamaria@gmail.com", "ozius1223423345", "/src/image2", "0735897635");
            Address address = new Address("Romania", "Timis", "Timisoara", "Strada Macilor 10", "Bloc 4, Scara F, ap 50", "300091");

            Buyer buyerToAdd = TestDataCreator.createBuyer(em, account);
            buyerToAdd.addAddress(address);

            return buyerToAdd;
        });

        //----Act
        doTransaction(em -> {
            Buyer mergedBuyer = em.merge(buyer);
            mergedBuyer.updateBuyer(
                    buyer.getAccount().getFirstName(),
                    buyer.getAccount().getLastName(),
                    "cosminaa@gmail.com",
                    buyer.getAccount().getPasswordHash(),
                    buyer.getAccount().getImageName(),
                    buyer.getAccount().getTelephone()
            );
        });

        //----Assert
        Buyer persistedBuyer = entityFinder.getTheOne(Buyer.class);
        Address persistedAddress = persistedBuyer.getAddresses().stream().findFirst().orElseThrow().getAddress();

        assertThat(persistedBuyer.getAccount().getEmail()).isEqualTo("cosminaa@gmail.com");

        assertThat(persistedBuyer.getAccount().getFirstName()).isEqualTo("Cosmina");
        assertThat(persistedBuyer.getAccount().getLastName()).isEqualTo("Maria");
        assertThat(persistedBuyer.getAccount().getPasswordHash()).isEqualTo("ozius1223423345");
        assertThat(persistedBuyer.getAccount().getImageName()).isEqualTo("/src/image2");
        assertThat(persistedBuyer.getAccount().getTelephone()).isEqualTo("0735897635");

        assertThat(persistedAddress.getCountry()).isEqualTo("Romania");
        assertThat(persistedAddress.getState()).isEqualTo("Timis");
        assertThat(persistedAddress.getCity()).isEqualTo("Timisoara");
        assertThat(persistedAddress.getAddressLine1()).isEqualTo("Strada Macilor 10");
        assertThat(persistedAddress.getAddressLine2()).isEqualTo("Bloc 4, Scara F, ap 50");
        assertThat(persistedAddress.getZipCode()).isEqualTo("300091");
    }


    @Test
    void test_buyer_remove(){

        //----Arrange
        Buyer buyer = doTransaction(em -> {
            TestDataCreator.createSellerBaseData(em);
            TestDataCreator.createCategoriesBaseData(em);
            Product product = TestDataCreator.createProduct(em, "orez", "pentru fiert", "src/image4", 12f, category1, seller1);

            UserAccount account = new UserAccount("Cosmina", "Maria", "cosminamaria@gmail.com", "ozius1223423345", "/src/image2", "0735897635");
            Address address = new Address("Romania", "Timis", "Timisoara", "Strada Macilor 10", "Bloc 4, Scara F, ap 50", "300091");

            Buyer buyerToAdd = TestDataCreator.createBuyer(em, account);
            buyerToAdd.addAddress(address);
            buyerToAdd.addFavorite(product);

            return buyerToAdd;
        });

        //----Act
        doTransaction(em -> {
            Buyer mergedBuyer = em.merge(buyer);
            em.remove(mergedBuyer);
        });

        //----Assert
        List<BuyerAddress> buyerAddress = new SimpleJpaRepository<>(BuyerAddress.class, emb).findAll();

        assertThat(buyerRepository.findAll().contains(buyer)).isFalse();
        assertThat(buyerAddress).isEmpty();
        assertThat(entityFinder.getFavorites()).isEmpty();
        assertThat(entityFinder.findAll(Product.class).size()).isEqualTo(1);
    }

    @Test
    void test_buyer_address_remove(){

        //----Arrange
        Buyer buyer = doTransaction(em -> {
            UserAccount account = new UserAccount("Cosmina", "Maria", "cosminamaria@gmail.com", "ozius1223423345", "/src/image2", "0735897635");
            Address address = new Address("Romania", "Timis", "Timisoara", "Strada Macilor 10", "Bloc 4, Scara F, ap 50", "300091");

            Buyer buyerToAdd = TestDataCreator.createBuyer(em, account);
            buyerToAdd.addAddress(address);

            return buyerToAdd;
        });


        //----Act
        doTransaction(em -> {
            Buyer mergedBuyer = em.merge(buyer);

            long addressId = mergedBuyer.getAddresses().stream().findFirst().orElseThrow().getId();

            mergedBuyer.removeAddress(addressId);
        });

        //----Assert
        Buyer persistedBuyer = entityFinder.getTheOne(Buyer.class);
        assertThat(persistedBuyer.getAddresses()).isEmpty();
    }

    @Test
    void test_add_favorites(){

        //----Arrange
        Buyer buyer = doTransaction(em -> {
            TestDataCreator.createSellerBaseData(em);
            TestDataCreator.createCategoriesBaseData(em);
            TestDataCreator.createProductsBaseData(em);

            UserAccount account = new UserAccount("Cosmina", "Maria", "cosminamaria@gmail.com", "ozius1223423345", "/src/image2", "0735897635");

            return TestDataCreator.createBuyer(em, account);
        });

        //----Act
        doTransaction(em -> {
            Buyer mergedBuyer = em.merge(buyer);

            //merge for many to many
            Product mergedProduct1 = em.merge(product1);
            Product mergedProduct2 = em.merge(product2);

            mergedBuyer.addFavorite(mergedProduct1);
            mergedBuyer.addFavorite(mergedProduct2);

        });

        //----Assert
        Buyer persistedBuyer = entityFinder.getTheOne(Buyer.class);

        assertThat(persistedBuyer.getFavoriteProducts().size()).isEqualTo(2);
    }

    @Test
    void test_add_favorite_details(){

        //----Arrange
        Product productToAdd = doTransaction(em -> {
            TestDataCreator.createSellerBaseData(em);
            TestDataCreator.createCategoriesBaseData(em);
            Product product = TestDataCreator.createProduct(em, "orez", "pentru fiert", "src/image4", 12f, category1, seller1);

            UserAccount account = new UserAccount("Cosmina", "Maria", "cosminamaria@gmail.com", "ozius1223423345", "/src/image2", "0735897635");
            TestDataCreator.createBuyer(em, account);

            return product;
        });

        //----Act
        doTransaction(em -> {
            Buyer mergedBuyer = new EntityFinder(em).getTheOne(Buyer.class);
            Product mergedProduct = em.merge(productToAdd);
            mergedBuyer.addFavorite(mergedProduct);
        });

        //----Assert
        Product persistedFavorite = entityFinder.getTheOne(Buyer.class).getFavoriteProducts().stream().findFirst().orElseThrow();
        Seller sellerProduct = productToAdd.getSeller();
        Category categoryProduct = productToAdd.getCategory();

        assertThat(persistedFavorite.getName()).isEqualTo("orez");
        assertThat(persistedFavorite.getDescription()).isEqualTo("pentru fiert");
        assertThat(persistedFavorite.getImageName()).isEqualTo("src/image4");
        assertThat(persistedFavorite.getPrice()).isEqualTo(12f);
        assertThat(persistedFavorite.getCategory()).isEqualTo(categoryProduct);
        assertThat(persistedFavorite.getSeller()).isEqualTo(sellerProduct);
    }

    @Test
    void test_add_favorite_same_product(){

        //----Arrange
        Product productToAdd = doTransaction(em -> {
            TestDataCreator.createSellerBaseData(em);
            TestDataCreator.createCategoriesBaseData(em);
            Product product = TestDataCreator.createProduct(em, "orez", "pentru fiert", "src/image4", 12f, category1, seller1);

            UserAccount account = new UserAccount("Cosmina", "Maria", "cosminamaria@gmail.com", "ozius1223423345", "/src/image2", "0735897635");
            Buyer addedBuyer = TestDataCreator.createBuyer(em, account);

            addedBuyer.addFavorite(product);

            return product;
        });

        //----Act
        Exception exception = doTransaction(em -> {
            Buyer mergedBuyer = new EntityFinder(em).getTheOne(Buyer.class);
            Product mergedProduct = em.merge(productToAdd);

            return assertThrows(IllegalItemException.class, ()-> mergedBuyer.addFavorite(mergedProduct));
        });

        //----Assert
        assertThat(exception.getMessage()).isEqualTo("can't add to favorites, items already exists");
    }

    @Test
    void test_remove_favorite_single_product_list(){

        //----Arrange
        Product productToRemove = doTransaction(em -> {
            TestDataCreator.createSellerBaseData(em);
            TestDataCreator.createCategoriesBaseData(em);
            Product product = TestDataCreator.createProduct(em, "orez", "pentru fiert", "src/image4", 12f, category1, seller1);

            UserAccount account = new UserAccount("Cosmina", "Maria", "cosminamaria@gmail.com", "ozius1223423345", "/src/image2", "0735897635");
            Buyer addedBuyer = TestDataCreator.createBuyer(em, account);

            addedBuyer.addFavorite(product);

            return product;
        });

        //----Act
        doTransaction(em -> {
            Buyer mergedBuyer = new EntityFinder(em).getTheOne(Buyer.class);

            Product mergedProduct = em.merge(productToRemove);
            mergedBuyer.removeFavorite(mergedProduct);
        });

        //----Assert
        Buyer persistedBuyer = entityFinder.getTheOne(Buyer.class);

        assertThat(persistedBuyer.getFavoriteProducts()).isEmpty();
    }

    @Test
    void test_remove_favorite_multiple_product_list(){

        //----Arrange
        Product productToRemove = doTransaction(em -> {
            TestDataCreator.createSellerBaseData(em);
            TestDataCreator.createCategoriesBaseData(em);

            Product product1 = TestDataCreator.createProduct(em, "orez", "pentru fiert", "src/image4", 12f, category1, seller1);
            Product product2 = TestDataCreator.createProduct(em, "grau", "pentru paine", "src/image20", 8f, category1, seller1);

            UserAccount account = new UserAccount("Cosmina", "Maria", "cosminamaria@gmail.com", "ozius1223423345", "/src/image2", "0735897635");
            Buyer addedBuyer = TestDataCreator.createBuyer(em, account);

            addedBuyer.addFavorite(product1);
            addedBuyer.addFavorite(product2);

            return product1;
        });

        //----Act
        doTransaction(em -> {
            Buyer mergedBuyer = new EntityFinder(em).getTheOne(Buyer.class);

            Product mergedProduct = em.merge(productToRemove);
            mergedBuyer.removeFavorite(mergedProduct);
        });

        //----Assert
        Buyer persistedBuyer = entityFinder.getTheOne(Buyer.class);

        assertThat(persistedBuyer.getFavoriteProducts().stream().anyMatch(item->item.equals(productToRemove))).isFalse();
        assertThat(persistedBuyer.getFavoriteProducts().size()).isEqualTo(1);
    }
}
