package org.example.controllers.impl;

import lombok.RequiredArgsConstructor;
import org.example.controllers.StockUserOperations;
import org.example.dtos.StockItemResponse;
import org.example.entities.StockEntity;
import org.example.enums.ColorEnum;
import org.example.enums.SizeEnum;
import org.example.enums.TypeEnum;
import org.example.mappers.StockItemMapper;
import org.example.services.impl.StockUserServiceImpl;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StockUserController implements StockUserOperations {

    private final StockUserServiceImpl stockUserService;
    private final StockItemMapper stockItemMapper;

    /**
     * Finds all stock items.
     *
     * @return List of StockItemResponse containing all stock items.
     */
    @Override
    public List<StockItemResponse> getAllStockItems() {
        List<StockEntity> stockEntities = stockUserService.findAllStockItems();
        return stockItemMapper.toDto(stockEntities);
    }

    /**
     * Finds stock items by name.
     *
     * @param name The name of the stock item to find.
     * @return List of StockItemResponse matching the provided name.
     */
    @Override
    public List<StockItemResponse> getStockItemsByName(String name) {
        return stockItemMapper.toDto(stockUserService.findStockItemByName(name));
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
    public List<StockItemResponse> getStockItemsByAttributes(String brand, ColorEnum color, SizeEnum size, TypeEnum type, Float minPrice, Float maxPrice) {
        List<StockEntity> stockEntities = stockUserService.findByAttributes(brand, color, size, type, minPrice, maxPrice);
        return stockItemMapper.toDto(stockEntities);
    }
}