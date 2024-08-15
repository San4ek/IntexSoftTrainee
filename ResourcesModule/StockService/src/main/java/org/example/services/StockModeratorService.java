package org.example.services;

import jakarta.validation.Valid;
import org.example.dtos.StockItemRequest;
import org.example.entities.StockEntity;

import java.util.UUID;

/**
 * Interface with business logic for stock items and moderator
 */
public interface StockModeratorService {

    StockEntity findById(UUID stockItemId);

    StockEntity createStockItem(@Valid StockItemRequest stockItemRequest);

    StockEntity updateStockItem(UUID stockItemId, @Valid StockItemRequest stockItemRequest);

    void removeStockItems(UUID stockItemId);

    void deleteStockItem(UUID stockItemId);
}
