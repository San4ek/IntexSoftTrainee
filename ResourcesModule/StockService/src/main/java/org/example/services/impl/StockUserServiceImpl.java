package org.example.services.impl;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dtos.StockItemAmount;
import org.example.entities.QStockEntity;
import org.example.entities.StockEntity;
import org.example.enums.ColorEnum;
import org.example.enums.SizeEnum;
import org.example.enums.TypeEnum;
import org.example.mappers.StockItemAmountMapper;
import org.example.repositories.StockRepository;
import org.example.services.StockUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class StockUserServiceImpl implements StockUserService {

    private final StockRepository stockRepository;
    private final StockItemAmountMapper stockItemAmountMapper;

    /**
     * Finds stock items by their name.
     *
     * @param name Name of the stock item to search for.
     * @return List of stock items matching the specified name.
     */
    @Override
    @Transactional(readOnly = true)
    public List<StockEntity> findStockItemsByName(final String name) {
        return stockRepository.getByProductName(name);
    }

    /**
     * Finds stock items matching the specified attributes.
     *
     * @param brand   Brand name of the stock item (optional).
     * @param color   Color of the stock item (optional).
     * @param size    Size of the stock item (optional).
     * @param type    Type of the stock item (optional).
     * @param minPrice Minimum price of the stock item (optional).
     * @param maxPrice Maximum price of the stock item (optional).
     * @return List of stock items matching the specified attributes.
     */
    @Override
    @Transactional(readOnly = true)
    public List<StockEntity> findByAttributes(final String brand,
                                              final ColorEnum color,
                                              final SizeEnum size,
                                              final TypeEnum type,
                                              final Float minPrice,
                                              final Float maxPrice) {
        QStockEntity stockItem = QStockEntity.stockEntity;
        BooleanBuilder builder = new BooleanBuilder();
        Optional.ofNullable(brand).ifPresent(b -> builder.and(stockItem.product.brand.name.likeIgnoreCase("%" + b + "%")));
        Optional.ofNullable(color).ifPresent(c -> builder.and(stockItem.color.eq(c)));
        Optional.ofNullable(size).ifPresent(s -> builder.and(stockItem.size.eq(s)));
        Optional.ofNullable(type).ifPresent(t -> builder.and(stockItem.product.type.eq(t)));
        Optional.ofNullable(minPrice).ifPresent(mp -> builder.and(stockItem.product.price.goe(mp)));
        Optional.ofNullable(maxPrice).ifPresent(mp -> builder.and(stockItem.product.price.loe(mp)));
        return (List<StockEntity>) stockRepository.findAll(builder);
    }

    /**
     * Checks the current amount of a stock item.
     *
     * @param stockItemId The ID of the stock item.
     * @return StockItemAmount DTO representing the current amount of the stock item.
     */
    @Override
    @Transactional(readOnly = true)
    public StockItemAmount checkStockItemAmount(UUID stockItemId) {
        return stockItemAmountMapper.toDto(stockRepository.getById(stockItemId));
    }

    /**
     * Decreases the amount of a stock item by the specified quantity.
     *
     * @param stockItemId The ID of the stock item.
     * @param amount The amount to decrease from the stock item.
     */
    @Override
    @Transactional
    public void decreaseStockItemAmount(UUID stockItemId, Long amount) {
        StockEntity stockEntity = stockRepository.getById(stockItemId);
        stockEntity.removeFromStock(amount);
        stockRepository.save(stockEntity);
    }

    /**
     * Increases the amount of a stock item by the specified quantity.
     *
     * @param stockItemId The ID of the stock item.
     * @param amount The amount to increase to the stock item.
     */
    @Override
    @Transactional
    public void increaseStockItemAmount(UUID stockItemId, Long amount) {
        StockEntity stockEntity = stockRepository.getById(stockItemId);
        stockEntity.addToStock(amount);
        stockRepository.save(stockEntity);
    }
}
