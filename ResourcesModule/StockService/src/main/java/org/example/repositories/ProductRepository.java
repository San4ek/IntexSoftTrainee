package org.example.repositories;

import org.example.entities.BrandEntity;
import org.example.entities.ProductEntity;
import org.example.enums.TypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {

    Boolean existsByNameAndTypeAndBrand(String name, TypeEnum type, BrandEntity brand);

    Optional<ProductEntity> findByName(String name);

    boolean existsByBrandId(UUID brandId);
}
