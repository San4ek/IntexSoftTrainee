package org.example.mappers;

import org.example.dtos.StockItemRequest;
import org.example.dtos.StockItemResponse;
import org.example.entities.ProductEntity;
import org.example.entities.StockEntity;
import org.example.repositories.ProductRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public abstract class StockItemMapper {

    @Autowired
    private ProductRepository productRepository;

    @Mapping(target = "product", source = "product")
    public abstract StockItemResponse toDto(StockEntity stockEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", source = "productId",qualifiedByName = "mapProduct")
    public abstract StockEntity toEntity(StockItemRequest stockItemRequest);

    @Mapping(target = "product", source = "product")
    public abstract void toDto(@MappingTarget StockItemResponse stockItemResponse, StockEntity stockEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", source = "productId",qualifiedByName = "mapProduct")
    public abstract void toEntity(@MappingTarget StockEntity stockEntity, StockItemRequest stockItemCreateRequest);

    public abstract List<StockItemResponse> toDto(List<StockEntity> stockEntityList);

    public abstract List<StockEntity> toEntity(List<StockItemRequest> stockItemRequestList);

    @Named("mapProduct")
    public ProductEntity mapProduct(UUID productId) {
        return productRepository.getById(productId);
    }

}
