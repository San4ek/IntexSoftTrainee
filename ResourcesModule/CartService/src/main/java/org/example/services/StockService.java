package org.example.services;

import org.example.dtos.StockItemAmount;

import java.util.UUID;

/**
 * Interface for stock operations.
 */
public interface StockService {

    StockItemAmount getStockItemById(UUID id);

    void changeStockAmount(UUID stockItemId, Long amount, String operation);
}
