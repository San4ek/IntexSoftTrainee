package org.example.testutils;

import org.example.dtos.OrderRequest;

import java.util.UUID;

public class OrderRequestBuilder {

    private UUID cartId = UUID.randomUUID();
    private UUID addressId = UUID.randomUUID();

    private OrderRequestBuilder() {}

    public static OrderRequestBuilder anOrderRequest() {
        return new OrderRequestBuilder();
    }

    public OrderRequestBuilder withCartId(final UUID cartId) {
        this.cartId = cartId;
        return this;
    }

    public OrderRequestBuilder withAddressId(final UUID addressId) {
        this.addressId = addressId;
        return this;
    }

    public OrderRequest build() {
        return new OrderRequest(cartId, addressId);
    }
}
