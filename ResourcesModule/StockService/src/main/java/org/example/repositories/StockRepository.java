package org.example.repositories;

import org.example.entities.ProductEntity;
import org.example.entities.StockEntity;
import org.example.enums.ColorEnum;
import org.example.enums.SizeEnum;
import org.example.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StockRepository extends BaseRepository<StockEntity, UUID> {

    Optional<List<StockEntity>> findByProductName(String name);

    default List<StockEntity> getByProductName(String name) {
        return findByProductName(name)
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new ObjectNotFoundException("Stock item not found with name " + name));
    }

    Boolean existsByIdAndAmountEquals(UUID id, Long amount);

    Boolean existsByProductIdAndAmountGreaterThan(UUID productId, Long amount);

    Boolean existsByColorAndSizeAndProduct(ColorEnum color, SizeEnum size, ProductEntity product);
}
