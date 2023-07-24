package com.ozius.internship.project.service;

import com.ozius.internship.project.entity.*;
import com.ozius.internship.project.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DemoService {

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

    @PersistenceUnit
    private EntityManagerFactory emf;
    @PersistenceContext
    private EntityManager em;

    public void createCategory() {
        Category category = new Category("secondCat", "imageName");
		categoryRepository.save(category);
    }

    @Transactional
    public void createProduct() {
        Category category = new Category("brutarie", "imageNameCat");
        categoryRepository.save(category);

        Address addr = new Address("romania", "maramures", "baia mare", "vasileLuc", "6/8","245129");
        UserAccount userAccount = new UserAccount("eri", "rusz", "e@gm", "aaaa", "imageNameAcc", "0747871208");
        userAccountRepository.save(userAccount);

        SellerInfo seller = new SellerInfo(addr, userAccount, "alias");
        sellerInfoRepository.save(seller);

        Product product = new Product("paine", "description", "imageNameProd", 2.5F, category, seller);
        productRepository.save(product);

    }

    public void createCart() {
        Cart cart = new Cart();
        cartRepository.save(cart);
    }

    @Transactional
    public CartItem addToCartService(BuyerInfo buyerInfo, Product product, float quantity) {
        BuyerInfo buyerManaged = buyerInfoRepository.save(buyerInfo);
        return buyerManaged.getCart().addToCart(product, quantity);
    }

    @Transactional
    public void getItemsFromCart(long buyerId, long prodId, long prodId2) {

        BuyerInfo buyerInfo = buyerInfoRepository.findById(buyerId).orElse(null);

        Product prod = productRepository.findById(prodId).orElse(null);
        Product prod2 = productRepository.findById(prodId2).orElse(null);

        this.addToCartService(buyerInfo, prod, 20F);
        this.addToCartService(buyerInfo, prod2, 32F);

        Cart cart = buyerInfo.getCart();
        cartRepository.save(cart);

        Set<CartItem> items = cart.getCartItems();
        for(CartItem item : items) {
            System.out.println(item.getProduct().getName() + " " + item.getQuantity());
        }

    }

    @Transactional
    public Set<Review> addReview(long buyerId, long productId, long sellerId) {
        BuyerInfo buyerInfo = buyerInfoRepository.findById(buyerId).orElse(null);

        Product product = productRepository.findById(productId).orElse(null);

        SellerInfo sellerInfo = sellerInfoRepository.findById(sellerId).orElse(null);

        sellerInfo.addReview(buyerInfo, "descrip", 2.5F, product);

        return sellerInfo.getReviews();

    }

    @Transactional
    public Cart getInfoAboutCart(long buyerId, long prodId, long prodId2) {

        BuyerInfo buyerInfo = buyerInfoRepository.findById(buyerId).orElse(null);

        Product prod = productRepository.findById(prodId).orElse(null);
        Product prod2 = productRepository.findById(prodId2).orElse(null);

        this.addToCartService(buyerInfo, prod, 20F);
        this.addToCartService(buyerInfo, prod2, 32F);

        Cart cart = buyerInfo.getCart();
        cartRepository.save(cart);
        return cart;
    }

    @Transactional
    public Cart modifyItem(long cartId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);

        cart.modifyItem(cartItemRepository.findByProductName("paine"), 5F);

        return cart;
    }

    @Transactional
    public double calculateTotalPrice(long cartId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        return cart.calculateTotalPrice();
    }

}
