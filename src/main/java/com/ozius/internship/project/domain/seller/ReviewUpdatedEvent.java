package com.ozius.internship.project.domain.seller;

public class ReviewUpdatedEvent {
    private final long productId;

    public ReviewUpdatedEvent(long productId) {
        this.productId = productId;
    }

    public long getProductId() {
        return productId;
    }

}
