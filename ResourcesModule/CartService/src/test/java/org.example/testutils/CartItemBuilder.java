package org.example.testutils;

import org.example.entities.CartEntity;
import org.example.entities.CartItemEntity;

import java.util.UUID;

public class CartItemBuilder {

    private UUID id = UUID.randomUUID();
    private CartEntity cart = CartBuilder.aCart().build();
    private UUID stockItemId = UUID.randomUUID();
    private Long amount = 10L;

    private CartItemBuilder() {
    }

    public static CartItemBuilder aCartItem() {
        return new CartItemBuilder();
    }

    public CartItemBuilder withId(final UUID id) {
        this.id = id;
        return this;
    }

    public CartItemBuilder withCart(final CartEntity cart) {
        this.cart = cart;
        return this;
    }

    public CartItemBuilder withStockItemId(final UUID stockItemId) {
        this.stockItemId = stockItemId;
        return this;
    }

    public CartItemBuilder withAmount(final Long amount) {
        this.amount = amount;
        return this;
    }

    public CartItemEntity build() {
        return new CartItemEntity(id, cart, stockItemId, amount);
    }
}
