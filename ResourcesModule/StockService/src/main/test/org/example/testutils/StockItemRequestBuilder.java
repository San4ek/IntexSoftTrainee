package org.example.testutils;

import org.example.dtos.StockItemRequest;
import org.example.enums.ColorEnum;
import org.example.enums.SizeEnum;

import java.util.UUID;

public class StockItemRequestBuilder {

    private ColorEnum color = ColorEnum.BLACK;
    private SizeEnum size = SizeEnum.SIZE_41;
    private UUID productId = UUID.randomUUID();
    private Long amount = 100L;

    private StockItemRequestBuilder() {}

    public static StockItemRequestBuilder aStockItemRequest() {
        return new StockItemRequestBuilder();
    }

    public StockItemRequestBuilder withColor(final ColorEnum color) {
        this.color = color;
        return this;
    }

    public StockItemRequestBuilder withSize(final SizeEnum size) {
        this.size = size;
        return this;
    }

    public StockItemRequestBuilder withProductId(final UUID productId) {
        this.productId = productId;
        return this;
    }

    public StockItemRequestBuilder withAmount(final Long amount) {
        this.amount = amount;
        return this;
    }

    public StockItemRequest build() {
        return new StockItemRequest(color, size, productId, amount);
    }
}
