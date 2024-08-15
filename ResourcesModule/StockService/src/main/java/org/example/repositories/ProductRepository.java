package org.example.repositories;

import org.example.entities.BrandEntity;
import org.example.entities.ProductEntity;
import org.example.enums.CurrencyEnum;
import org.example.enums.TypeEnum;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends BaseRepository<ProductEntity, UUID> {

    Boolean existsByNameAndTypeAndBrand(String name, TypeEnum type, BrandEntity brand);

    Boolean existsByBrandId(UUID brandId);

    Optional<ProductEntity> findByNameAndTypeAndBrandAndCurrency(String name, TypeEnum type, BrandEntity brandEntity, CurrencyEnum currency);
}
