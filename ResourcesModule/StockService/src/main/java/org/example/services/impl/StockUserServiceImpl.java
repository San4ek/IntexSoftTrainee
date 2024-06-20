package org.example.services.impl;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.example.entities.QStockEntity;
import org.example.entities.StockEntity;
import org.example.enums.ColorEnum;
import org.example.enums.SizeEnum;
import org.example.enums.TypeEnum;
import org.example.repositories.StockRepository;
import org.example.services.StockUserService;
import org.example.validation.ValidationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StockUserServiceImpl implements StockUserService {

    private final StockRepository stockRepository;

    @Override
    @Transactional(readOnly = true)
    public List<StockEntity> findAllStockItems() {
        return stockRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public StockEntity findStockItemByName(final String name) {
        return stockRepository.findByProductName(name).orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockEntity> findByAttributes(final UUID brand,
                                              final  ColorEnum color,
                                              final SizeEnum size,
                                              final TypeEnum type,
                                              final Float minPrice,
                                              final Float maxPrice) {
        QStockEntity stockItem = QStockEntity.stockEntity;
        BooleanBuilder builder = new BooleanBuilder();
        Optional.ofNullable(brand).ifPresent(b -> builder.and(stockItem.product.brand.id.eq(b)));
        Optional.ofNullable(color).ifPresent(c -> builder.and(stockItem.color.eq(c)));
        Optional.ofNullable(size).ifPresent(s -> builder.and(stockItem.size.eq(s)));
        Optional.ofNullable(type).ifPresent(t -> builder.and(stockItem.product.type.eq(t)));
        Optional.ofNullable(minPrice).ifPresent(mp -> builder.and(stockItem.product.price.goe(mp)));
        Optional.ofNullable(maxPrice).ifPresent(mp -> builder.and(stockItem.product.price.loe(mp)));
        return (List<StockEntity>) stockRepository.findAll(builder);
    }


}
