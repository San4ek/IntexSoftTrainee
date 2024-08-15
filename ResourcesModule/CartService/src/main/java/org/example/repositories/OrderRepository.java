package org.example.repositories;

import org.example.entities.OrderEntity;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends BaseRepository<OrderEntity, UUID> {

    Boolean existsByAddressId(final UUID addressId);

    void deleteByAddressId(final UUID addressId);
}
