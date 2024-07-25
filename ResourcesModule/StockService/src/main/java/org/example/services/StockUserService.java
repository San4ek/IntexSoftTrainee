package org.example.services;

import org.example.dtos.StockItemAmount;
import org.example.entities.StockEntity;
import org.example.enums.ColorEnum;
import org.example.enums.SizeEnum;
import org.example.enums.StockOperationEnum;
import org.example.enums.TypeEnum;

import java.util.List;
import java.util.UUID;

/**
 * Interface with business logic for stock items and user
 */
public interface StockUserService {

    List<StockEntity> findStockItemsByName(String name);

    List<StockEntity> findByAttributes(String brand, ColorEnum color, SizeEnum size, TypeEnum type, Double minPrice, Double maxPrice);

    StockItemAmount checkStockItemAmount(UUID stockItemId);

    void changeStockItemAmount(UUID stockItemId, Long amount, StockOperationEnum operation);
}
