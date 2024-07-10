package org.example.repositories;

import org.example.entities.ProductEntity;
import org.example.entities.StockEntity;
import org.example.enums.ColorEnum;
import org.example.enums.SizeEnum;
import org.example.exceptions.StockNotExistException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StockRepository extends BaseRepository<StockEntity, UUID> {

    Optional<List<StockEntity>> findByProductName(String name);

    default List<StockEntity> getByProductName(String name) {
        return findByProductName(name).orElseThrow(() -> new StockNotExistException("Stock item not found with name " + name));
    }

    Boolean existsByIdAndAmountEquals(UUID id, long amount);

    Boolean existsByProductIdAndAmountGreaterThan(UUID productId, long amount);

    Boolean existsByColorAndSizeAndProduct(ColorEnum color, SizeEnum size, ProductEntity product);
}
