package org.example.controllers;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithJwt;
import lombok.RequiredArgsConstructor;
import org.example.dtos.PagedStockItemsResponse;
import org.example.dtos.StockItemAmount;
import org.example.dtos.StockItemRequest;
import org.example.entities.BrandEntity;
import org.example.entities.ProductEntity;
import org.example.entities.StockEntity;
import org.example.enums.ColorEnum;
import org.example.enums.SizeEnum;
import org.example.enums.StockOperationEnum;
import org.example.enums.TypeEnum;
import org.example.exceptions.ObjectNotFoundException;
import org.example.mappers.StockItemMapper;
import org.example.repositories.BrandRepository;
import org.example.repositories.ProductRepository;
import org.example.repositories.StockRepository;
import org.example.services.impl.StockUserServiceImpl;
import org.example.testutils.BrandBuilder;
import org.example.testutils.EndpointsUrls;
import org.example.testutils.ProductBuilder;
import org.example.testutils.StockItemRequestBuilder;
import org.example.validation.ValidationStockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Transactional
public class StockUserControllerTest {

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @MockBean
    private StockUserServiceImpl stockUserService;

    @MockBean
    private ValidationStockService validationStockService;

    private final MockMvc mockMvc;
    private final StockRepository stockRepository;
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;
    private final StockItemMapper stockItemMapper;
    private StockEntity stockEntity;

    @BeforeEach
    void setUp() {
        final BrandEntity brandEntity = brandRepository.save(BrandBuilder.aBrand().build());
        final ProductEntity productEntity = productRepository.save(ProductBuilder.aProduct().withBrand(brandEntity).build());
        final StockItemRequest stockItemRequest = StockItemRequestBuilder.aStockItemRequest().withProductId(productEntity.getId()).build();
        stockEntity = stockRepository.save(stockItemMapper.toEntity(stockItemRequest));
    }

    @Test
    @WithJwt("jwt/user.json")
    @DisplayName("Tests GET /api/stock-items/search/name with valid name")
    void getStockItemsByName_200Expected() throws Exception {
        final String stockItemName = stockEntity.getProduct().getName();
        final Page<StockEntity> response = new PageImpl<>(List.of(stockEntity), PageRequest.of(0, 10), 1);
        when(stockUserService.findStockItemsByName(eq(stockItemName), anyInt(), anyInt())).thenReturn(response);
        final PagedStockItemsResponse pagedResponse = stockItemMapper.toDto(response);
        mockMvc.perform(MockMvcRequestBuilders.get(EndpointsUrls.STOCK_USER_NAME.getPath() + stockItemName)
                        .param("name", stockItemName)
                        .param("page", "0")
                        .param("pageSize", "10"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(pagedResponse.getTotalPages()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(pagedResponse.getTotalElements()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageNumber").value(pagedResponse.getPageNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageSize").value(pagedResponse.getPageSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(stockEntity.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].product.name").value(stockEntity.getProduct().getName()));
    }

    @Test
    @WithJwt("jwt/user.json")
    @DisplayName("Tests GET /api/stock-items/search/attributes with valid attributes")
    void getStockItemsByAttributes_200Expected() throws Exception {
        final String brand = stockEntity.getProduct().getBrand().getName();
        final ColorEnum color = stockEntity.getColor();
        final SizeEnum size = stockEntity.getSize();
        final TypeEnum type = stockEntity.getProduct().getType();
        final Double minPrice = 10.0;
        final Double maxPrice = 100.0;
        final Page<StockEntity> response = new PageImpl<>(List.of(stockEntity), PageRequest.of(0, 10), 1);
        when(stockUserService.findByAttributes(eq(brand), eq(color), eq(size), eq(type), eq(minPrice), eq(maxPrice), anyInt(), anyInt()))
                .thenReturn(response);
        final PagedStockItemsResponse pagedResponse = stockItemMapper.toDto(response);
        mockMvc.perform(MockMvcRequestBuilders.get(EndpointsUrls.STOCK_USER_ATTRIBUTES.getPath())
                        .param("brand", brand)
                        .param("color", color.toString())
                        .param("size", size.toString())
                        .param("type", type.toString())
                        .param("minPrice", minPrice.toString())
                        .param("maxPrice", maxPrice.toString())
                        .param("page", "0")
                        .param("pageSize", "10"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(pagedResponse.getTotalPages()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(pagedResponse.getTotalElements()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageNumber").value(pagedResponse.getPageNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageSize").value(pagedResponse.getPageSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(stockEntity.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].product.name").value(stockEntity.getProduct().getName()));
    }

    @Test
    @WithJwt("jwt/user.json")
    @DisplayName("Tests GET /api/stock-items/{id}/amount with valid ID")
    void checkStockItemAmount_200Expected() throws Exception {
        final UUID stockItemId = stockEntity.getId();
        final StockItemAmount stockItemAmount = new StockItemAmount(stockItemId, 100L, 19.99D);
        when(stockUserService.checkStockItemAmount(stockItemId)).thenReturn(stockItemAmount);
        mockMvc.perform(MockMvcRequestBuilders.get(EndpointsUrls.STOCK_USER_GET_PUT.getPath() + stockItemId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(stockItemAmount.getAmount().intValue()));
    }

    @Test
    @WithJwt("jwt/user.json")
    @DisplayName("Tests GET /api/stock-items/{id}/amount with invalid ID")
    void checkStockItemAmount_InvalidId_404Expected() throws Exception {
        final UUID invalidStockItemId = UUID.randomUUID();
        when(stockUserService.checkStockItemAmount(invalidStockItemId)).thenThrow(new ObjectNotFoundException("Stock item not found"));
        mockMvc.perform(MockMvcRequestBuilders.get(EndpointsUrls.STOCK_USER_GET_PUT.getPath() + invalidStockItemId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithJwt("jwt/user.json")
    @DisplayName("Tests POST /api/stock-items/{id}/change-amount with valid data")
    void changeStockAmount_200Expected() throws Exception {
        final UUID stockItemId = stockEntity.getId();
        final Long amount = 5L;
        final StockOperationEnum operation = StockOperationEnum.INCREASE;
        final String apiKey = "valid-api-key";
        doNothing().when(stockUserService).changeStockItemAmount(stockItemId, amount, operation);
        doNothing().when(validationStockService).validateApiKey(apiKey);
        mockMvc.perform(MockMvcRequestBuilders.put(EndpointsUrls.STOCK_USER_GET_PUT.getPath() + stockItemId)
                        .header("CART-API-KEY", apiKey)
                        .param("amount", amount.toString())
                        .param("operation", operation.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithJwt("jwt/user.json")
    @DisplayName("Tests POST /api/stock-items/{id}/change-amount with missing API key")
    void changeStockAmount_MissingApiKey_400Expected() throws Exception {
        final UUID stockItemId = stockEntity.getId();
        final Long amount = 5L;
        StockOperationEnum operation = StockOperationEnum.INCREASE;
        mockMvc.perform(MockMvcRequestBuilders.put(EndpointsUrls.STOCK_USER_GET_PUT.getPath() + stockItemId)
                        .param("amount", amount.toString())
                        .param("operation", operation.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
