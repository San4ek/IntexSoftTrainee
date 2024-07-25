package org.example.controllers.impl;

import lombok.RequiredArgsConstructor;
import org.example.controllers.StockUserOperations;
import org.example.dtos.StockItemAmount;
import org.example.dtos.StockItemResponse;
import org.example.entities.StockEntity;
import org.example.enums.ColorEnum;
import org.example.enums.SizeEnum;
import org.example.enums.StockOperationEnum;
import org.example.enums.TypeEnum;
import org.example.mappers.StockItemMapper;
import org.example.services.impl.StockUserServiceImpl;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Controller with methods for users to use stock items
 */
@RestController
@RequiredArgsConstructor
public class StockUserController implements StockUserOperations {

    private final StockUserServiceImpl stockUserService;
    private final StockItemMapper stockItemMapper;

    /**
     * Finds stock items by name.
     *
     * @param name The name of the stock item to find.
     * @return List of StockItemResponse matching the provided name.
     */
    @Override
    public List<StockItemResponse> getStockItemsByName(final String name) {
        return stockItemMapper.toDto(stockUserService.findStockItemsByName(name));
    }

    /**
     * Finds stock items by specified attributes.
     *
     * @param brand    The brand of the stock item.
     * @param color    The color of the stock item.
     * @param size     The size of the stock item.
     * @param type     The type of the stock item.
     * @param minPrice The minimum price of the stock item.
     * @param maxPrice The maximum price of the stock item.
     * @return List of StockItemResponse matching the specified attributes.
     */
    @Override
    public List<StockItemResponse> getStockItemsByAttributes(final String brand, final ColorEnum color, final SizeEnum size, final TypeEnum type, final Double minPrice, final Double maxPrice) {
        List<StockEntity> stockEntities = stockUserService.findByAttributes(brand, color, size, type, minPrice, maxPrice);
        return stockItemMapper.toDto(stockEntities);
    }

    @Override
    public StockItemAmount checkStockItemAmount(final UUID stockItemId) {
        return stockUserService.checkStockItemAmount(stockItemId);
    }

    @Override
    public void changeStockAmount(final UUID stockItemId, final Long amount, final StockOperationEnum operation) {
        stockUserService.changeStockItemAmount(stockItemId, amount, operation);
    }
}
