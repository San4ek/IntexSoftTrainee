package org.example.testutils;

import org.example.entities.CartEntity;
import org.example.entities.CartItemEntity;
import org.example.enums.CurrencyEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class CartBuilder {

    private UUID id = UUID.randomUUID();
    private UUID userId = UUID.randomUUID();
    private CurrencyEnum currency = CurrencyEnum.EUR;
    private List<CartItemEntity> items = new ArrayList<>();

    private CartBuilder() {
    }

    public static CartBuilder aCart() {
        return new CartBuilder();
    }

    public CartBuilder withId(final UUID id) {
        this.id = id;
        return this;
    }

    public CartBuilder withUserId(final UUID userId) {
        this.userId = userId;
        return this;
    }

    public CartBuilder withCurrency(final CurrencyEnum currency) {
        this.currency = currency;
        return this;
    }

    public CartBuilder withItems(final List<CartItemEntity> items) {
        this.items = items;
        return this;
    }

    public CartEntity build() {
        return new CartEntity(id, userId, currency, items);
    }
}
