package org.example.repositories;

import org.example.entities.ProductEntity;
import org.example.entities.StockEntity;
import org.example.enums.ColorEnum;
import org.example.enums.SizeEnum;
import org.example.exceptions.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StockRepository extends BaseRepository<StockEntity, UUID> {

    Page<StockEntity> findByProductName(final String name, final Pageable pageable);

    Boolean existsByIdAndAmountEquals(final UUID id, final Long amount);

    Boolean existsByProductIdAndAmountGreaterThan(final UUID productId, final Long amount);

    Boolean existsByColorAndSizeAndProduct(final ColorEnum color, final SizeEnum size, final ProductEntity product);

    Boolean existsByProductName(final String name);

    void deleteByProductId(final UUID productId);
}
