package com.ozius.internship.project.entity.seller;

public class ReviewUpdatedEvent {
    private final long productId;

    public ReviewUpdatedEvent(long productId) {
        this.productId = productId;
    }

    public long getProductId() {
        return productId;
    }

}
