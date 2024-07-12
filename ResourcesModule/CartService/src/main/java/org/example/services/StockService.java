package org.example.services;

import org.example.dtos.StockItemAmount;

import java.util.UUID;

/**
 * Interface for stock operations.
 */
public interface StockService {

    StockItemAmount getStockItemById(UUID id);

    void increaseStock(UUID stockItemId, Long amount);

    void decreaseStock(UUID stockItemId, Long amount);
}
