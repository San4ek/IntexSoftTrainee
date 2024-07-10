package org.example.repositories;

import org.example.entities.BrandEntity;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BrandRepository extends BaseRepository<BrandEntity, UUID> {

    Boolean existsByName(String name);
}
