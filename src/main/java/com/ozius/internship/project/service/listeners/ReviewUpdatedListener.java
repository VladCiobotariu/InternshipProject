package com.ozius.internship.project.service.listeners;

import com.ozius.internship.project.domain.seller.ReviewAddedEvent;
import com.ozius.internship.project.domain.seller.ReviewUpdatedEvent;
import com.ozius.internship.project.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class ReviewUpdatedListener {

    private final ProductService productService;

    public ReviewUpdatedListener(ProductService productService) {
        this.productService = productService;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public void handleUpdateReview(ReviewUpdatedEvent event) {
        productService.recalculateProductRating(event.getProductId());
    }
}
