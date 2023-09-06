package com.ozius.internship.project.repository;

import com.ozius.internship.project.entity.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
//    @Query("SELECT DISTINCT c FROM Cart c LEFT JOIN FETCH c.cartItems ci LEFT JOIN FETCH ci.product")
//    List<Cart> findAllWithCartItemsAndProducts();
    Cart findCartByBuyer_Id(long id);
}
