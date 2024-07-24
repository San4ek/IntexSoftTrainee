package org.example.repositories;

import org.example.entities.BrandEntity;
import org.example.entities.ProductEntity;
import org.example.enums.CurrencyEnum;
import org.example.enums.TypeEnum;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends BaseRepository<ProductEntity, UUID> {

    Boolean existsByNameAndTypeAndBrand(String name, TypeEnum type, BrandEntity brand);

    Boolean existsByNameAndTypeAndBrandAndPriceAndCurrency(String name, TypeEnum type, BrandEntity brand, Double price, CurrencyEnum currency);

    Boolean existsByBrandId(UUID brandId);
}
