package com.ozius.internship.project;

import com.ozius.internship.project.entity.*;
import com.ozius.internship.project.repository.*;
import com.ozius.internship.project.service.DemoService;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class ProjectApplicationTests {

	@PersistenceUnit
	private EntityManagerFactory emf;
	@PersistenceContext
	private EntityManager em;

	@Autowired
	private DemoService demoService;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private UserAccountRepository userAccountRepository;

	@Autowired
	private SellerInfoRepository sellerInfoRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private BuyerInfoRepository buyerInfoRepository;

	@BeforeEach
	@Transactional
	void setUp() {
		Category category = new Category("brutarie", "imageNameCat");
		categoryRepository.save(category);

		BuyerInfo buyerInfo = new BuyerInfo(new UserAccount("eriku", "sz", "e@gm", "aaaa", "imageNameAcc", "0747871208"));
		buyerInfoRepository.save(buyerInfo);

		SellerInfo seller = new SellerInfo(
				new Address("romania", "mm", "bm", "vl", "6/8","245"),
				new UserAccount("eriku", "sz", "e@gm", "aaaa", "imageNameAcc", "0747871208"),
				"alias");
		sellerInfoRepository.save(seller);

		Product product = new Product("paine", "description", "imageNameProd", 2.5F, category, seller);
		productRepository.save(product);

		Product product2 = new Product("croissant", "description", "imageNameProd", 2.5F, category, seller);
		productRepository.save(product2);
	}

	@Test
	void createCategory() {
		demoService.createCategory();

		Category newCat = categoryRepository.findAll().get(0);
		assertThat(newCat).isNotNull();
		assertThat(newCat.getName()).isEqualTo("brutarie");
	}

	@Test
	void createProduct() {
		demoService.createProduct();

		Product newProd = productRepository.findAll().get(0);
		assertThat(newProd).isNotNull();
		assertThat(newProd.getName()).isEqualTo("paine");

	}

	@Test
	void createCart() {
		demoService.createCart();

		Cart newCart = cartRepository.findAll().get(0);
		assertThat(newCart).isNotNull();
	}

	@Test
	void getItemsFromCart() {
		BuyerInfo buyerInfo = buyerInfoRepository.findAll().get(0);

		Product product = productRepository.findAll().get(0);

		Product product2 = productRepository.findAll().get(1);

		demoService.getItemsFromCart(buyerInfo.getId(), product.getId(), product2.getId());
	}

	@Test
	void addReview() {
		BuyerInfo buyerInfo = buyerInfoRepository.findAll().get(0);

        Product product = productRepository.findAll().get(0);

        SellerInfo sellerInfo = sellerInfoRepository.findAll().get(0);

		Set<Review> reviews = demoService.addReview(buyerInfo.getId(), product.getId(), sellerInfo.getId());

		assertThat(sellerInfo).isNotNull();

		assertThat(reviews).hasSize(1);
		Review review = reviews.iterator().next();
		assertThat(review.getDescription()).isEqualTo("descrip");
	}

	@Test
	void getInfoAboutCart(){
		BuyerInfo buyerInfo = buyerInfoRepository.findAll().get(0);

		Product product = productRepository.findAll().get(0);

		Product product2 = productRepository.findAll().get(1);

		Cart cart = demoService.getInfoAboutCart(buyerInfo.getId(), product.getId(), product2.getId());

		System.out.println(demoService.calculateTotalPrice(cart.getId()));

		CartItem cartItem = cartItemRepository.findByProductName("paine");
		System.out.println("quantity before modification " + cartItem.getQuantity());

		Cart cart2 = demoService.modifyItem(cartItem.getId());

		CartItem cartItem2 = cartItemRepository.findByProductName("paine");
		System.out.println("quantity after modification " + cartItem2.getQuantity());

		System.out.println(demoService.calculateTotalPrice(cart2.getId()));

	}

}
