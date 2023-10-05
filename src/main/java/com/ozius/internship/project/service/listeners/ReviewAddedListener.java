package com.ozius.internship.project.service.listeners;

import com.ozius.internship.project.entity.seller.ReviewAddedEvent;
import com.ozius.internship.project.service.ProductService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class ReviewAddedListener {

    private final ProductService productService;

    public ReviewAddedListener(ProductService productService) {
        this.productService = productService;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleAddReview(ReviewAddedEvent event) {
        System.out.println("event listened");
        productService.recalculateProductRating(event.getProductId());
    }
}
