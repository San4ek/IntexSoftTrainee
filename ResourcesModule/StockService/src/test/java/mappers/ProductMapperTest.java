package mappers;

import org.example.dtos.ProductRequest;
import org.example.dtos.ProductResponse;
import org.example.entities.BrandEntity;
import org.example.entities.ProductEntity;
import org.example.enums.CurrencyEnum;
import org.example.enums.TypeEnum;
import org.example.mappers.ProductMapper;
import org.example.mappers.StockItemMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProductMapperTest {

    private ProductMapper productMapper;

    @BeforeEach
    public void setUp() {
        productMapper = Mappers.getMapper(ProductMapper.class);
    }

    @Test
    public void testToDto() {
        // Создание тестовой сущности ProductEntity
        UUID brandId = UUID.randomUUID();
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setId(brandId);

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(UUID.randomUUID());
        productEntity.setName("Test Product");
        productEntity.setType(TypeEnum.valueOf("SNEAKERS")); // Использование valueOf для перечисления TypeEnum
        productEntity.setBrand(brandEntity);
        productEntity.setCurrency(CurrencyEnum.valueOf("USD")); // Использование valueOf для перечисления CurrencyEnum
        productEntity.setPrice(100.0f);

        // Преобразование в DTO
        ProductResponse productResponse = productMapper.toDto(productEntity);

        // Проверка преобразования
        assertNotNull(productResponse);
        assertEquals(productEntity.getName(), productResponse.getName());
        assertEquals(productEntity.getType(), productResponse.getType());
        assertEquals(productEntity.getBrand().getId(), productResponse.getBrandId());
        assertEquals(productEntity.getCurrency(), productResponse.getCurrency());
        assertEquals(productEntity.getPrice(), productResponse.getPrice());
    }

    @Test
    public void testToEntity() {
        // Создание тестового DTO ProductRequest
        UUID brandId = UUID.randomUUID();
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("Test Product");
        productRequest.setType(TypeEnum.valueOf("BOOTS")); // Использование перечисления TypeEnum напрямую
        productRequest.setBrandId(brandId);
        productRequest.setCurrency(CurrencyEnum.valueOf("BYN")); // Использование перечисления CurrencyEnum напрямую
        productRequest.setPrice(100.0f);

        // Преобразование в сущность ProductEntity
        ProductEntity productEntity = productMapper.toEntity(productRequest);

        // Проверка преобразования
        assertNotNull(productEntity);
        assertEquals(productRequest.getName(), productEntity.getName());
        assertEquals(productRequest.getType(), productEntity.getType());
        assertEquals(productRequest.getBrandId(), productEntity.getBrand().getId());
        assertEquals(productRequest.getCurrency(), productEntity.getCurrency());
        assertEquals(productRequest.getPrice(), productEntity.getPrice());
    }

}
