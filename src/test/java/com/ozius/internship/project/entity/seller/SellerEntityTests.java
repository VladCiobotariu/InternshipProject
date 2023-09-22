package com.ozius.internship.project.entity.seller;

import com.ozius.internship.project.JpaBaseEntity;
import com.ozius.internship.project.TestDataCreator;
import com.ozius.internship.project.entity.*;
import com.ozius.internship.project.entity.exception.IllegalSellerDetails;
import com.ozius.internship.project.entity.order.Order;
import com.ozius.internship.project.entity.product.Product;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

import static com.ozius.internship.project.TestDataCreator.Addresses.address1;
import static com.ozius.internship.project.TestDataCreator.Buyers.buyer1;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


public class SellerEntityTests extends JpaBaseEntity {

    private JpaRepository<Seller, Long> sellerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void createTestData(EntityManager em) {
        this.sellerRepository = new SimpleJpaRepository<>(Seller.class, emb);
    }

    @Test
    void test_add_seller_farmer(){

        //----Act
        Seller addedSeller = doTransaction(em -> {
            Address address = new Address(
                    "Romania",
                    "Timis",
                    "Timisoara",
                    "Strada Circumvalatiunii nr 4",
                    "Bloc 3 Scara B Ap 12",
                    "303413");

            Seller seller = new Seller(
                    address,
                    new UserAccount(
                            "Vlad",
                            "Ciobotariu",
                            "vladciobotariu@gmail.com",
                            "/src/image1",
                            "0734896512"
                    ),
                    "Mega Fresh SRL",
                    SellerType.LOCAL_FARMER
            );
            em.persist(seller);

            return seller;
        });

        //----Assert
        Seller persistedSeller = entityFinder.getTheOne(Seller.class);

        assertThat(persistedSeller.getAccount().getFirstName()).isEqualTo("Vlad");
        assertThat(persistedSeller.getAccount().getLastName()).isEqualTo("Ciobotariu");
        assertThat(persistedSeller.getAccount().getEmail()).isEqualTo("vladciobotariu@gmail.com");
        assertThat(persistedSeller.getAccount().getPasswordHash()).isNull();
        assertThat(persistedSeller.getAccount().getImageName()).isEqualTo("/src/image1");
        assertThat(persistedSeller.getAccount().getTelephone()).isEqualTo("0734896512");
        assertThat(persistedSeller.getAlias()).isEqualTo("Mega Fresh SRL");
        assertThat(persistedSeller.getLegalAddress().getCountry()).isEqualTo("Romania");
        assertThat(persistedSeller.getLegalAddress().getState()).isEqualTo("Timis");
        assertThat(persistedSeller.getLegalAddress().getCity()).isEqualTo("Timisoara");
        assertThat(persistedSeller.getLegalAddress().getAddressLine1()).isEqualTo("Strada Circumvalatiunii nr 4");
        assertThat(persistedSeller.getLegalAddress().getAddressLine2()).isEqualTo("Bloc 3 Scara B Ap 12");
        assertThat(persistedSeller.getLegalAddress().getZipCode()).isEqualTo("303413");
        assertThat(persistedSeller.getSellerType()).isEqualTo(SellerType.LOCAL_FARMER);
        assertThat(persistedSeller.getLegalDetails()).isNull();

        assertThat(persistedSeller).isEqualTo(addedSeller);
        assertThat(persistedSeller.getReviews()).isEmpty();
        assertThat(persistedSeller.calculateRating()).isEqualTo(0);
    }

    @Test
    void test_add_seller_password(){

        //----Arrange
        Seller addedSeller = doTransaction(em -> {
            Address address = new Address(
                    "Romania",
                    "Timis",
                    "Timisoara",
                    "Strada Circumvalatiunii nr 4",
                    "Bloc 3 Scara B Ap 12",
                    "303413");

            Seller seller = new Seller(
                    address,
                    new UserAccount(
                            "Vlad",
                            "Ciobotariu",
                            "vladciobotariu@gmail.com",
                            "/src/image1",
                            "0734896512"
                    ),
                    "Mega Fresh SRL",
                    SellerType.LOCAL_FARMER
            );
            em.persist(seller);

            return seller;
        });

        //----Act
        doTransaction(em -> {
            Seller mergedSeller = em.merge(addedSeller);
            mergedSeller.getAccount().setInitialPassword(passwordEncoder.encode("1234"));
        });

        //----Assert
        Seller persistedSeller = entityFinder.getTheOne(Seller.class);

        assertTrue(passwordEncoder.matches("1234", persistedSeller.getAccount().getPasswordHash()));
    }

    @Test
    void test_update_seller(){

        //----Arrange
        Seller seller = doTransaction(em -> {

            Address address = new Address("Romania", "Timis", "Timisoara", "Strada Circumvalatiunii nr 4", "Bloc 3 Scara B Ap 12", "303413");
            UserAccount userAccount = new UserAccount("Vlad", "Ciobotariu", "vladciobotariu@gmail.com", "/src/image1", "0734896512");
            userAccount.setInitialPassword(passwordEncoder.encode("1234"));

            return TestDataCreator.createSellerFarmer(em, address, userAccount, "honey srl");
        });

        //----Act
        doTransaction(em -> {
            Seller mergedSeller = em.merge(seller);
            UserAccount account = mergedSeller.getAccount();

            mergedSeller.getAccount().updateAccount(
                    "Vlad Cristian",
                    account.getLastName(),
                    account.getEmail(),
                    account.getImageName(),
                    account.getTelephone());
        });

        //----Assert
        Seller persistedSeller = entityFinder.getTheOne(Seller.class);
        assertThat(persistedSeller.getAccount().getFirstName()).isEqualTo("Vlad Cristian");

        assertThat(persistedSeller.getAccount().getLastName()).isEqualTo("Ciobotariu");
        assertThat(persistedSeller.getAccount().getEmail()).isEqualTo("vladciobotariu@gmail.com");
        assertTrue(passwordEncoder.matches("1234", persistedSeller.getAccount().getPasswordHash()));
        assertThat(persistedSeller.getAccount().getImageName()).isEqualTo("/src/image1");
        assertThat(persistedSeller.getAccount().getTelephone()).isEqualTo("0734896512");
        assertThat(persistedSeller.getAlias()).isEqualTo("honey srl");
        assertThat(persistedSeller.getLegalAddress().getCountry()).isEqualTo("Romania");
        assertThat(persistedSeller.getLegalAddress().getState()).isEqualTo("Timis");
        assertThat(persistedSeller.getLegalAddress().getCity()).isEqualTo("Timisoara");
        assertThat(persistedSeller.getLegalAddress().getAddressLine1()).isEqualTo("Strada Circumvalatiunii nr 4");
        assertThat(persistedSeller.getLegalAddress().getAddressLine2()).isEqualTo("Bloc 3 Scara B Ap 12");
        assertThat(persistedSeller.getLegalAddress().getZipCode()).isEqualTo("303413");

        assertThat(persistedSeller.getLegalDetails()).isNull();
    }

    @Test
    void test_remove_seller(){

        //----Arrange
        Seller sellerToAdd = doTransaction(em -> {
            Address addressSeller = new Address("Romania", "Timis", "Timisoara", "Strada Circumvalatiunii nr 4", "Bloc 3 Scara B Ap 12", "303413");
            UserAccount userAccount = new UserAccount("Vlad", "Ciobotariu", "vladciobotariu1@gmail.com", "/src/image1", "0734896512");

            Seller seller = TestDataCreator.createSellerCompany(em, addressSeller, userAccount, "bio", SellerType.PFA, new LegalDetails("MEGA FRESH SA", "RO37745609", new RegistrationNumber(CompanyType.F, 41, 34, LocalDate.now())));

            TestDataCreator.createBuyerBaseData(em, passwordEncoder);
            Address addressBuyer = new Address("Romania", "Timis", "Timisoara", "Strada Macilor 10", "Bloc 4, Scara F, ap 50", "300091");

            TestDataCreator.createOrder(em,addressBuyer, buyer1, seller);

            return seller;
        });


        //----Act
        Seller removedSeller = doTransaction(em -> {

            Seller mergedSeller = em.merge(sellerToAdd);
            em.remove(mergedSeller);

            return mergedSeller;
        });

        //----Assert
        List<Product> persistedProducts = entityFinder.getProductsBySeller(removedSeller);
        Order persistedOrder = entityFinder.getTheOne(Order.class);

        assertThat(sellerRepository.findAll().contains(removedSeller)).isFalse();
        assertThat(persistedProducts).isEmpty();

        assertThat(persistedOrder).isNotNull();
        assertThat(persistedOrder.getSeller()).isNull();

    }

    @Test
    void test_add_seller_pfa_with_legal_details_null(){

        //----Arrange
        doTransaction(em -> {
            TestDataCreator.createAddresses();
        });

        //----Act
        Exception exception = doTransaction(em -> {
            UserAccount account =  new UserAccount("Vlad", "Ciobotariu", "vladciobotariu@gmail.com", "/src/image1", "0734896512");
            return assertThrows(IllegalSellerDetails.class, ()->{
                Seller seller = new Seller(address1, account, "Mega Fresh SRL", SellerType.PFA, null);
                em.persist(seller);
            });
        });

        //----Assert
        assertTrue(exception.getMessage().contains("legalDetails can't be null if company or pfa"));

    }

    @Test
    void test_add_seller_company_with_legal_details_null(){

        //----Arrange
        doTransaction(em -> {
            TestDataCreator.createAddresses();
        });

        //----Act
        Exception exception = doTransaction(em -> {
            UserAccount account =  new UserAccount("Vlad", "Ciobotariu", "vladciobotariu@gmail.com", "/src/image1", "0734896512");
            return assertThrows(IllegalSellerDetails.class, ()->{
                Seller seller = new Seller(address1, account, "Mega Fresh SRL", SellerType.COMPANY, null);
                em.persist(seller);
            });
        });

        //----Assert
        assertTrue(exception.getMessage().contains("legalDetails can't be null if company or pfa"));

    }

    @Test
    void test_add_seller_legal_details(){

        //----Arrange
        doTransaction(em -> {
            TestDataCreator.createAddresses();
        });

        //----Act
        doTransaction(em -> {
            UserAccount account =  new UserAccount("Vlad", "Ciobotariu", "vladciobotariu@gmail.com", "/src/image1", "0734896512");
            LegalDetails legalDetails = new LegalDetails("MEGA FRESH SA", "RO37745609", new RegistrationNumber(CompanyType.F, 41, 34, LocalDate.now()));

            Seller seller = new Seller(address1, account, "Mega Fresh SRL", SellerType.PFA, legalDetails);
            em.persist(seller);
        });

        //----Assert
        LegalDetails persistedLegalDetails = entityFinder.getTheOne(Seller.class).getLegalDetails();

        assertThat(persistedLegalDetails.getName()).isEqualTo("MEGA FRESH SA");
        assertThat(persistedLegalDetails.getCui()).isEqualTo("RO37745609");
        assertThat(persistedLegalDetails.getRegistrationNumber().getCompanyType()).isEqualTo(CompanyType.F);
        assertThat(persistedLegalDetails.getRegistrationNumber().getNumericCodeByState()).isEqualTo(41);
        assertThat(persistedLegalDetails.getRegistrationNumber().getSerialNumber()).isEqualTo(34);
        assertThat(persistedLegalDetails.getRegistrationNumber().getDateOfRegistration()).isEqualTo(LocalDate.now());
    }

    @Test
    void test_update_seller_legal_details(){

        //----Arrange
        Seller sellerToUpdate = doTransaction(em -> {

            Address address = new Address("Romania", "Timis", "Timisoara", "Strada Circumvalatiunii nr 4", "Bloc 3 Scara B Ap 12", "303413");

            UserAccount account =  new UserAccount("Vlad", "Ciobotariu", "vladciobotariu@gmail.com", "/src/image1", "0734896512");
            account.setInitialPassword(passwordEncoder.encode("1234"));
            LegalDetails legalDetails = new LegalDetails("MEGA FRESH SA", "RO37745609", new RegistrationNumber(CompanyType.C, 41, 34, LocalDate.now()));

            Seller seller = new Seller(address, account, "Mega Fresh SRL", SellerType.PFA, legalDetails);
            em.persist(seller);

            return seller;
        });

        //----Act
        doTransaction(em -> {
            Seller mergedSeller = em.merge(sellerToUpdate);
            LegalDetails legalDetails = mergedSeller.getLegalDetails();
            LegalDetails legalDetailsToAdd = new LegalDetails(legalDetails.getName(), "RO37745999", legalDetails.getRegistrationNumber());

            mergedSeller.updateSeller(
                    legalDetailsToAdd,
                    mergedSeller.getLegalAddress()
            );
        });

        //----Assert
        Seller persistedSeller = entityFinder.getTheOne(Seller.class);
        LegalDetails persistedLegalDetails = persistedSeller.getLegalDetails();

        assertThat(persistedSeller.getLegalAddress().getCountry()).isEqualTo("Romania");
        assertThat(persistedSeller.getLegalAddress().getState()).isEqualTo("Timis");
        assertThat(persistedSeller.getLegalAddress().getCity()).isEqualTo("Timisoara");
        assertThat(persistedSeller.getLegalAddress().getAddressLine1()).isEqualTo("Strada Circumvalatiunii nr 4");
        assertThat(persistedSeller.getLegalAddress().getAddressLine2()).isEqualTo("Bloc 3 Scara B Ap 12");
        assertThat(persistedSeller.getLegalAddress().getZipCode()).isEqualTo("303413");

        assertThat(persistedSeller.getSellerType()).isEqualTo(SellerType.PFA);

        assertThat(persistedLegalDetails.getName()).isEqualTo("MEGA FRESH SA");
        assertThat(persistedLegalDetails.getCui()).isEqualTo("RO37745999");
        assertThat(persistedLegalDetails.getRegistrationNumber().getCompanyType()).isEqualTo(CompanyType.C);
        assertThat(persistedLegalDetails.getRegistrationNumber().getNumericCodeByState()).isEqualTo(41);
        assertThat(persistedLegalDetails.getRegistrationNumber().getSerialNumber()).isEqualTo(34);
        assertThat(persistedLegalDetails.getRegistrationNumber().getDateOfRegistration()).isEqualTo(LocalDate.now());

    }

    @Test
    void test_update_seller_legal_address(){

        //----Arrange
        Seller sellerToUpdate = doTransaction(em -> {

            Address address = new Address("Romania", "Timis", "Timisoara", "Strada Circumvalatiunii nr 4", "Bloc 3 Scara B Ap 12", "303413");

            UserAccount account =  new UserAccount("Vlad", "Ciobotariu", "vladciobotariu@gmail.com", "/src/image1", "0734896512");
            account.setInitialPassword(passwordEncoder.encode("1234"));
            LegalDetails legalDetails = new LegalDetails("MEGA FRESH SA", "RO37745609", new RegistrationNumber(CompanyType.J, 41, 34, LocalDate.now()));

            Seller seller = new Seller(address, account, "Mega Fresh SRL", SellerType.PFA, legalDetails);
            em.persist(seller);

            return seller;
        });

        //----Act
        doTransaction(em -> {
            Seller mergedSeller = em.merge(sellerToUpdate);
            LegalDetails legalDetails = mergedSeller.getLegalDetails();
            Address legalAddress = mergedSeller.getLegalAddress();

            Address legalAddressToAdd = new Address("Italy", legalAddress.getState(), legalAddress.getCity(), legalAddress.getAddressLine1(), legalAddress.getAddressLine2(), legalAddress.getZipCode());

            mergedSeller.updateSeller(
                    legalDetails,
                    legalAddressToAdd
            );
        });

        //----Assert
        Seller persistedSeller = entityFinder.getTheOne(Seller.class);
        LegalDetails persistedLegalDetails = persistedSeller.getLegalDetails();

        assertThat(persistedSeller.getLegalAddress().getCountry()).isEqualTo("Italy");
        assertThat(persistedSeller.getLegalAddress().getState()).isEqualTo("Timis");
        assertThat(persistedSeller.getLegalAddress().getCity()).isEqualTo("Timisoara");
        assertThat(persistedSeller.getLegalAddress().getAddressLine1()).isEqualTo("Strada Circumvalatiunii nr 4");
        assertThat(persistedSeller.getLegalAddress().getAddressLine2()).isEqualTo("Bloc 3 Scara B Ap 12");
        assertThat(persistedSeller.getLegalAddress().getZipCode()).isEqualTo("303413");

        assertThat(persistedSeller.getSellerType()).isEqualTo(SellerType.PFA);

        assertThat(persistedLegalDetails.getName()).isEqualTo("MEGA FRESH SA");
        assertThat(persistedLegalDetails.getCui()).isEqualTo("RO37745609");
        assertThat(persistedLegalDetails.getRegistrationNumber().getCompanyType()).isEqualTo(CompanyType.J);
        assertThat(persistedLegalDetails.getRegistrationNumber().getNumericCodeByState()).isEqualTo(41);
        assertThat(persistedLegalDetails.getRegistrationNumber().getSerialNumber()).isEqualTo(34);
        assertThat(persistedLegalDetails.getRegistrationNumber().getDateOfRegistration()).isEqualTo(LocalDate.now());
    }
}