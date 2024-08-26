package org.example.testutils;

import org.example.dtos.CartItemRequest;

import java.util.UUID;

public class CartItemRequestBuilder {

    private UUID cartId = UUID.randomUUID();
    private UUID cartItemId = UUID.randomUUID();
    private Long amount = 10L;

    private CartItemRequestBuilder() {
    }

    public static CartItemRequestBuilder aCartItemRequest() {
        return new CartItemRequestBuilder();
    }

    public CartItemRequestBuilder withCartId(final UUID cartId) {
        this.cartId = cartId;
        return this;
    }

    public CartItemRequestBuilder withCartItemId(final UUID cartItemId) {
        this.cartItemId = cartItemId;
        return this;
    }

    public CartItemRequestBuilder withAmount(final Long amount) {
        this.amount = amount;
        return this;
    }

    public CartItemRequest build() {
        return new CartItemRequest(cartId, cartItemId, amount);
    }
}
