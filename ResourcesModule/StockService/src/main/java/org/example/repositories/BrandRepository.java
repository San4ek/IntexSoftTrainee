package org.example.repositories;

import org.example.entities.BrandEntity;
import org.example.exceptions.BrandNotExistException;
import org.example.exceptions.ObjectNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BrandRepository extends BaseRepository<BrandEntity, UUID> {

    Boolean existsByName(String name);

    Optional<BrandEntity> findByName(String name);

    default BrandEntity getByName(String name) {
        return findByName(name).orElseThrow(() -> new ObjectNotFoundException("Object not found with name: " + name));
    }
}
