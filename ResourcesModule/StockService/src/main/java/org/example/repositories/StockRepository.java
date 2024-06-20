package org.example.repositories;

import org.example.entities.ProductEntity;
import org.example.entities.StockEntity;
import org.example.enums.ColorEnum;
import org.example.enums.SizeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StockRepository extends JpaRepository<StockEntity, UUID>, QuerydslPredicateExecutor<StockEntity> {

    Optional<StockEntity> findByProductName(String name);
    Boolean existsByIdAndAmountEquals(UUID id, long amount);
    Boolean existsByProductIdAndAmountGreaterThan(UUID productId, long amount);
    Boolean existsByColorAndSizeAndProduct(ColorEnum color, SizeEnum size, ProductEntity product);
}
