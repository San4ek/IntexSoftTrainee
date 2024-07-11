package org.example.services;

import org.example.dtos.StockItemRequest;
import org.example.entities.StockEntity;

import java.util.UUID;

/**
 * Interface with business logic for stock items and moderator
 */
public interface StockModeratorService {

    StockEntity findById(UUID stockItemId);

    StockEntity createStockItem(StockItemRequest stockItemRequest);

    StockEntity updateStockItem(UUID stockItemId, StockItemRequest stockItemRequest);

    void deleteStockItem(UUID stockItemId);
}
