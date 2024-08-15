package org.example.testutils;

import org.example.entities.BrandEntity;
import org.example.entities.ProductEntity;
import org.example.enums.CurrencyEnum;
import org.example.enums.TypeEnum;

import java.util.UUID;

public class ProductBuilder {

    private UUID id = UUID.randomUUID();
    private String name = "Product Name";
    private TypeEnum type = TypeEnum.SNEAKERS;
    private CurrencyEnum currency = CurrencyEnum.EUR;
    private Double price = 10.99D;
    private BrandEntity brand = BrandBuilder.aBrand().build();

    private ProductBuilder() {}

    public static ProductBuilder aProduct() {
        return new ProductBuilder();
    }

    public ProductBuilder withId(final UUID id) {
        this.id = id;
        return this;
    }

    public ProductBuilder withName(final String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder withType(final TypeEnum type) {
        this.type = type;
        return this;
    }

    public ProductBuilder withBrand(final BrandEntity brand) {
        this.brand = brand;
        return this;
    }

    public ProductBuilder withCurrency(final CurrencyEnum currency) {
        this.currency = currency;
        return this;
    }

    public ProductBuilder withPrice(final Double price) {
        this.price = price;
        return this;
    }

    public ProductEntity build() {
        return new ProductEntity(id, name, type, brand, currency, price);
    }
}
