package org.example.services;

import org.example.dtos.StockItemAmount;
import org.example.entities.StockEntity;
import org.example.enums.ColorEnum;
import org.example.enums.SizeEnum;
import org.example.enums.StockOperationEnum;
import org.example.enums.TypeEnum;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

/**
 * Interface with business logic for stock items and user
 */
public interface StockUserService {

    Page<StockEntity> findStockItemsByName(final String name, final Integer page, final Integer pageSize);

    Page<StockEntity> findByAttributes(final String brand,
                                       final ColorEnum color,
                                       final SizeEnum size,
                                       final TypeEnum type,
                                       final Double minPrice,
                                       final Double maxPrice,
                                       final Integer page,
                                       final Integer pageSize);

    StockItemAmount checkStockItemAmount(UUID stockItemId);

    void changeStockItemAmount(UUID stockItemId, Long amount, StockOperationEnum operation);
}
