package org.example.testutils;

import org.example.dtos.CartRequest;
import org.example.enums.CurrencyEnum;

public class CartRequestBuilder {

    private CurrencyEnum currency = CurrencyEnum.USD;

    private CartRequestBuilder() {}

    public static CartRequestBuilder aCartRequest() {
        return new CartRequestBuilder();
    }

    public CartRequestBuilder withCurrency(final CurrencyEnum currency) {
        this.currency = currency;
        return this;
    }

    public CartRequest build() {
        return new CartRequest(currency);
    }
}
