package org.example.repositories;

import org.example.entities.CartEntity;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CartRepository extends BaseRepository<CartEntity, UUID> {

    void deleteByUserId(UUID accountId);
}
