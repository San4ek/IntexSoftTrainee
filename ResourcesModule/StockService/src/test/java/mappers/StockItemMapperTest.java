package mappers;

import org.example.dtos.StockItemRequest;
import org.example.dtos.StockItemResponse;
import org.example.entities.ProductEntity;
import org.example.entities.StockEntity;
import org.example.enums.ColorEnum;
import org.example.enums.SizeEnum;
import org.example.mappers.StockItemMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StockItemMapperTest {

    private StockItemMapper stockItemMapper;

    @BeforeEach
    public void setUp() {
        stockItemMapper = Mappers.getMapper(StockItemMapper.class);
    }

    @Test
    public void testToDto() {
        // Создание тестовой сущности StockEntity
        UUID productId = UUID.randomUUID();
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(productId);

        StockEntity stockEntity = new StockEntity();
        stockEntity.setId(UUID.randomUUID());
        stockEntity.setColor(ColorEnum.valueOf("RED"));
        stockEntity.setSize(SizeEnum.valueOf("SIZE_40"));
        stockEntity.setProduct(productEntity);
        stockEntity.setAmount(10L);

        // Преобразование в DTO
        StockItemResponse stockItemResponse = stockItemMapper.toDto(stockEntity);

        // Проверка преобразования
        assertNotNull(stockItemResponse);
        assertEquals(stockEntity.getColor(), stockItemResponse.getColor());
        assertEquals(stockEntity.getSize(), stockItemResponse.getSize());
        assertEquals(stockEntity.getProduct().getId(), stockItemResponse.getProductId());
        assertEquals(stockEntity.getAmount(), stockItemResponse.getAmount());
    }

    @Test
    public void testToEntity() {
        // Создание тестового DTO StockItemRequest
        UUID productId = UUID.randomUUID();
        StockItemRequest stockItemRequest = new StockItemRequest();
        stockItemRequest.setColor(ColorEnum.valueOf("RED"));
        stockItemRequest.setSize(SizeEnum.valueOf("SIZE_40"));
        stockItemRequest.setProductId(productId);
        stockItemRequest.setAmount(10L);

        // Преобразование в сущность
        StockEntity stockEntity = stockItemMapper.toEntity(stockItemRequest);

        // Проверка преобразования
        assertNotNull(stockEntity);
        assertEquals(stockItemRequest.getColor(), stockEntity.getColor());
        assertEquals(stockItemRequest.getSize(), stockEntity.getSize());
        assertEquals(stockItemRequest.getProductId(), stockEntity.getProduct().getId());
        assertEquals(stockItemRequest.getAmount(), stockEntity.getAmount());
    }

    @Test
    public void testToDtoList() {
        // Создание списка тестовых сущностей StockEntity
        UUID productId1 = UUID.randomUUID();
        ProductEntity productEntity1 = new ProductEntity();
        productEntity1.setId(productId1);

        StockEntity stockEntity1 = new StockEntity();
        stockEntity1.setId(UUID.randomUUID());
        stockEntity1.setColor(ColorEnum.valueOf("RED"));
        stockEntity1.setSize(SizeEnum.valueOf("SIZE_40"));
        stockEntity1.setProduct(productEntity1);
        stockEntity1.setAmount(10L);

        UUID productId2 = UUID.randomUUID();
        ProductEntity productEntity2 = new ProductEntity();
        productEntity2.setId(productId2);

        StockEntity stockEntity2 = new StockEntity();
        stockEntity2.setId(UUID.randomUUID());
        stockEntity2.setColor(ColorEnum.valueOf("BLACK"));
        stockEntity2.setSize(SizeEnum.valueOf("SIZE_41"));
        stockEntity2.setProduct(productEntity2);
        stockEntity2.setAmount(20L);

        List<StockEntity> stockEntities = List.of(stockEntity1, stockEntity2);

        // Преобразование в список DTO
        List<StockItemResponse> stockItemResponses = stockItemMapper.toDto(stockEntities);

        // Проверка преобразования
        assertNotNull(stockItemResponses);
        assertEquals(2, stockItemResponses.size());

        StockItemResponse response1 = stockItemResponses.get(0);
        assertEquals(stockEntity1.getColor(), response1.getColor());
        assertEquals(stockEntity1.getSize(), response1.getSize());
        assertEquals(stockEntity1.getProduct().getId(), response1.getProductId());
        assertEquals(stockEntity1.getAmount(), response1.getAmount());

        StockItemResponse response2 = stockItemResponses.get(1);
        assertEquals(stockEntity2.getColor(), response2.getColor());
        assertEquals(stockEntity2.getSize(), response2.getSize());
        assertEquals(stockEntity2.getProduct().getId(), response2.getProductId());
        assertEquals(stockEntity2.getAmount(), response2.getAmount());
    }

    @Test
    public void testToEntityList() {
        // Создание списка тестовых DTO StockItemRequest
        UUID productId1 = UUID.randomUUID();
        StockItemRequest stockItemRequest1 = new StockItemRequest();
        stockItemRequest1.setColor(ColorEnum.valueOf("GREEN"));
        stockItemRequest1.setSize(SizeEnum.valueOf("SIZE_40"));
        stockItemRequest1.setProductId(productId1);
        stockItemRequest1.setAmount(10L);

        UUID productId2 = UUID.randomUUID();
        StockItemRequest stockItemRequest2 = new StockItemRequest();
        stockItemRequest2.setColor(ColorEnum.valueOf("BLACK"));
        stockItemRequest2.setSize(SizeEnum.valueOf("SIZE_41"));
        stockItemRequest2.setProductId(productId2);
        stockItemRequest2.setAmount(20L);

        List<StockItemRequest> stockItemRequests = List.of(stockItemRequest1, stockItemRequest2);

        // Преобразование в список сущностей
        List<StockEntity> stockEntities = stockItemMapper.toEntity(stockItemRequests);

        // Проверка преобразования
        assertNotNull(stockEntities);
        assertEquals(2, stockEntities.size());

        StockEntity entity1 = stockEntities.get(0);
        assertEquals(stockItemRequest1.getColor(), entity1.getColor());
        assertEquals(stockItemRequest1.getSize(), entity1.getSize());
        assertEquals(stockItemRequest1.getProductId(), entity1.getProduct().getId());
        assertEquals(stockItemRequest1.getAmount(), entity1.getAmount());

        StockEntity entity2 = stockEntities.get(1);
        assertEquals(stockItemRequest2.getColor(), entity2.getColor());
        assertEquals(stockItemRequest2.getSize(), entity2.getSize());
        assertEquals(stockItemRequest2.getProductId(), entity2.getProduct().getId());
        assertEquals(stockItemRequest2.getAmount(), entity2.getAmount());
    }
}
