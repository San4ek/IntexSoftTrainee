package org.example.testutils;

import org.example.dtos.ProductRequest;
import org.example.enums.CurrencyEnum;
import org.example.enums.TypeEnum;

import java.util.UUID;

public class ProductRequestBuilder {

    private String name = "Product Name";
    private TypeEnum type = TypeEnum.SNEAKERS;
    private CurrencyEnum currency = CurrencyEnum.EUR;
    private Double price = 10.99D;
    private UUID brandId = UUID.randomUUID();

    private ProductRequestBuilder() {}

    public static ProductRequestBuilder aProductRequest() {
        return new ProductRequestBuilder();
    }

    public ProductRequestBuilder withName(final String name) {
        this.name = name;
        return this;
    }

    public ProductRequestBuilder withType(final TypeEnum type) {
        this.type = type;
        return this;
    }

    public ProductRequestBuilder withBrandId(final UUID brandId) {
        this.brandId = brandId;
        return this;
    }

    public ProductRequestBuilder withCurrency(final CurrencyEnum currency) {
        this.currency = currency;
        return this;
    }

    public ProductRequestBuilder withPrice(final Double price) {
        this.price = price;
        return this;
    }

    public ProductRequest build() {
        return new ProductRequest(name, type, currency, price, brandId);
    }
}
