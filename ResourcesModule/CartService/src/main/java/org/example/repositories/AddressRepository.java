package org.example.repositories;

import org.example.entities.AddressEntity;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AddressRepository extends BaseRepository<AddressEntity, UUID> {

    boolean existsByAddress(String address);
}
