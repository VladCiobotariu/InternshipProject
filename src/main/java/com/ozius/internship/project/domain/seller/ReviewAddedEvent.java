package com.ozius.internship.project.domain.seller;

public class ReviewAddedEvent {
    private final long productId;

    public ReviewAddedEvent(long productId) {
        this.productId = productId;
    }

    public long getProductId() {
        return productId;
    }

}
