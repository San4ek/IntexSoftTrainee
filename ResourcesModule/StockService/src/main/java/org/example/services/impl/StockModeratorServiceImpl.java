package org.example.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dtos.StockItemRequest;
import org.example.entities.StockEntity;
import org.example.mappers.StockItemMapper;
import org.example.repositories.StockRepository;
import org.example.services.StockModeratorService;
import org.example.validation.ValidationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockModeratorServiceImpl implements StockModeratorService {

    //interfaces
    private final StockRepository stockRepository;
    private final ValidationService validationService;
    private final StockItemMapper stockItemCreateRequestMapper;

    @Transactional(readOnly = true)
    public StockEntity findById(final UUID stockItemId) {
        return stockRepository.findById(stockItemId).orElseThrow();
    }

    @Transactional
    public StockEntity createStockItem(final StockItemRequest stockItemRequest) {
        validationService.validateStockItemForCreate(stockItemRequest);
        return stockRepository.saveAndFlush(stockItemCreateRequestMapper.toEntity(stockItemRequest));
    }

    @Transactional
    public StockEntity updateStockItem(final UUID stockItemId, final StockItemRequest stockItemRequest) {
        validationService.validateStockItemForUpdate(stockItemId, stockItemRequest);
        StockEntity stockEntity = stockItemCreateRequestMapper.toEntity(stockItemRequest);
        StockEntity existingStockEntity = stockRepository.findById(stockItemId).orElseThrow();
        existingStockEntity.setSize(stockEntity.getSize());
        existingStockEntity.setColor(stockEntity.getColor());
        existingStockEntity.setAmount(stockEntity.getAmount());
        existingStockEntity.setProduct(stockEntity.getProduct());
        return stockRepository.saveAndFlush(existingStockEntity);
    }

    @Transactional
    public void deleteStockItem(final UUID stockItemId) {
        validationService.validateStockItemForDelete(stockItemId);
        stockRepository.deleteById(stockItemId);
    }
}
