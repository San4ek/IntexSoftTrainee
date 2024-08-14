package org.example.testutils;

import org.example.entities.ProductEntity;
import org.example.entities.StockEntity;
import org.example.enums.ColorEnum;
import org.example.enums.SizeEnum;

import java.util.UUID;

public class StockItemBuilder {

    private UUID id = UUID.randomUUID();
    private ColorEnum color = ColorEnum.BLACK;
    private SizeEnum size = SizeEnum.SIZE_41;
    private ProductEntity product = ProductBuilder.aProduct().build();
    private Long amount = 100L;

    private StockItemBuilder() {}

    public static StockItemBuilder aStockItem() {
        return new StockItemBuilder();
    }

    public StockItemBuilder withId(final UUID id) {
        this.id = id;
        return this;
    }

    public StockItemBuilder withColor(final ColorEnum color) {
        this.color = color;
        return this;
    }

    public StockItemBuilder withSize(final SizeEnum size) {
        this.size = size;
        return this;
    }

    public StockItemBuilder withProduct(final ProductEntity product) {
        this.product = product;
        return this;
    }

    public StockItemBuilder withAmount(final Long amount) {
        this.amount = amount;
        return this;
    }

    public StockEntity build() {
        return new StockEntity(id, color, size, product, amount);
    }
}
