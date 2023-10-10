package com.ozius.internship.project.service.listeners;

import com.ozius.internship.project.domain.seller.ReviewAddedEvent;
import com.ozius.internship.project.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class ReviewAddedListener {

    private final ProductService productService;

    public ReviewAddedListener(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Requires new transaction because current transaction resources are not yet closed but commited already. Any changes made would not be persisted.
     * See Warning message on TransactionalEventListener.
     * @param event
     */
   @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
   @Transactional(value = Transactional.TxType.REQUIRES_NEW) //Requires new will always open a new transaction even if one already exists.
    public void handleAddReview(ReviewAddedEvent event) {
        productService.recalculateProductRating(event.getProductId()); // the service will reuse the transaction opened by listener. (see default TxType = REQUIRED)
    }
}
