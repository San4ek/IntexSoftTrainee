package org.example.repositories;

import org.example.entities.OrderEntity;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends BaseRepository<OrderEntity, UUID> {

    boolean existsByAddressId(UUID addressId);

    void deleteByAddressId(UUID addressId);
}
