package org.example.repositories;

import org.example.entities.CartItemEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartItemRepository extends BaseRepository<CartItemEntity, UUID> {

    CartItemEntity findByCartIdAndStockId(UUID cartId, UUID stockId);

    Boolean existsByCartIdAndStockId(UUID cartId, UUID stockId);

    void deleteByStockId(UUID stockId);

    Boolean existsByStockId(UUID stockId);
}
