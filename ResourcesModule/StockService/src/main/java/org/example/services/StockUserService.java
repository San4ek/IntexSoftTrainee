package org.example.services;

import org.example.entities.StockEntity;
import org.example.enums.ColorEnum;
import org.example.enums.SizeEnum;
import org.example.enums.TypeEnum;

import java.util.List;
import java.util.UUID;

public interface StockUserService {

    List<StockEntity> findAllStockItems();

    StockEntity findStockItemByName(String name);

    List<StockEntity> findByAttributes(UUID brand, ColorEnum color, SizeEnum size, TypeEnum type, Float minPrice, Float maxPrice);

}
