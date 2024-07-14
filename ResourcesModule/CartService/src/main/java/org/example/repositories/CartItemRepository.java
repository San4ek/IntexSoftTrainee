package org.example.repositories;

import org.example.entities.CartItemEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartItemRepository extends BaseRepository<CartItemEntity, UUID> {

    List<CartItemEntity> findByCartId(UUID cartId);

    void deleteByCartIdAndStockId(UUID cartId, UUID stockId);

    CartItemEntity findByCartIdAndStockId(UUID cartId, UUID stockId);
}
