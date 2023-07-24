package com.ozius.internship.project;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.ozius.internship.project.entity.*;
import com.ozius.internship.project.repository.*;
import com.ozius.internship.project.service.BuyerService;
import com.ozius.internship.project.service.SellerService;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Propagation;

import java.util.Set;

@SpringBootTest
@ActiveProfiles("test")
class ProjectApplicationTests {


	@Autowired
	private SellerService sellerService;

	@Autowired
	private BuyerService buyerService;

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private SellerRepository sellerRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private BuyerRepository buyerRepository;

	@BeforeEach
	@Transactional
	void setUp() {

		sellerService.addSeller(
				new SellerInfo(
						new Address("Romania",
								"Timis",
								"Timisoara",
								"Strada Circumvalatiunii nr 4",
								"Bloc 3 Scara B Ap 12",
								"3003413"),
				new UserAccount("Vlad",
						"Ciobotariu",
						"vladciobotariu@gmail.com",
						"ozius12345",
						"/src/image1",
						"0734896512"),
				"Mega Fresh SRL"
		));

		buyerService.addBuyer(
				new BuyerInfo(
						new UserAccount("Cosmina",
								"Maria",
								"cosminamaria@gmail.com",
								"ozius1223423345",
								"/src/image2",
								"0735897635")
				));

		Category cereale = new Category("Cereale", "src/image9");

		em.persist(cereale);

		SellerInfo sellerInfo = sellerRepository.findAll().stream().findFirst().get();

		Product product1 = new Product("orez", "pentru fiert", "src/image4", 12.7f, cereale, sellerInfo);

		sellerService.addProduct(product1);

		Product product2 = new Product("grau", "pentru paine", "src/image20", 8.2f, cereale, sellerInfo);

		sellerService.addProduct(product2);

	}

	@Test
	void addSeller() {

		sellerService.addSeller(new SellerInfo(new Address("Romania", "Timis", "Timisoara", "Strada Circumvalatiunii nr 4", "Bloc 3 Scara B Ap 12", "3003413"),
												new UserAccount("Vlad", "Ciobotariu", "vladciobotariu@gmail.com", "ozius12345", "/src/image1", "0734896512"),
												"Mega Fresh SRL"
		));

		System.out.println(accountRepository.findById(1l).stream().findFirst().get().getEmail());
		System.out.println(sellerRepository.findById(1l).stream().findFirst().get().getAlias());

	}

	@Test
	@Transactional
	@Commit
	void addProduct(){

		SellerInfo sellerInfo = sellerRepository.findAll().stream().findFirst().get();

		Category cereale = categoryRepository.findByName("Cereale");

		System.out.println(cereale);

		Product product = new Product("orez", "pentru fiert", "src/image4", 12.7f, cereale, sellerInfo);

		sellerService.addProduct(product);

//		System.out.println(sellerInfo.getProducts());

		System.out.println(productRepository.findAll().stream().findFirst().get());

		System.out.println(sellerRepository.findAll().stream().findFirst().get());

	}

	@Test
	@Transactional
	void addToCart(){

		BuyerInfo buyer = buyerRepository.findAll().stream().findFirst().get();

		Product product1 = productRepository.findByName("orez").stream().findFirst().get();

		Product product2 = productRepository.findByName("grau").stream().findFirst().get();

		buyerService.addToCart(buyer, product1, 5f);
		buyerService.addToCart(buyer, product2, 10f);

		buyerService.addToCart(buyer, product1, 2f);

		System.out.println("Total price: " + buyerRepository.findAll().stream().findFirst().get().getCart().calculateTotalPrice());

		Cart cart = buyer.getCart();

		Set<CartItem> cartItems = buyer.getCart().getCartItems();

		cart.modifyItem(buyerService.findCartItemByName(buyer,"grau"), 2f);

		cartItems.forEach(cartItem -> System.out.println(cartItem.getProduct().getName() + ": " + buyer.getCart().calculateItemPrice(cartItem)));

	}

	@Test
	void favorites(){

	}

}
