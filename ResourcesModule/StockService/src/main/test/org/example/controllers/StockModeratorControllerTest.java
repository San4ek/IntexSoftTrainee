package org.example.controllers;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithJwt;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.dtos.StockItemRequest;
import org.example.entities.BrandEntity;
import org.example.entities.ProductEntity;
import org.example.entities.StockEntity;
import org.example.mappers.StockItemMapper;
import org.example.repositories.BrandRepository;
import org.example.repositories.ProductRepository;
import org.example.repositories.StockRepository;
import org.example.services.impl.StockModeratorServiceImpl;
import org.example.testutils.BrandBuilder;
import org.example.testutils.EndpointsUrls;
import org.example.testutils.ProductBuilder;
import org.example.testutils.StockItemRequestBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Transactional
public class StockModeratorControllerTest {

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @MockBean
    private StockModeratorServiceImpl stockModeratorService;

    private final MockMvc mockMvc;
    private final StockRepository stockRepository;
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;
    private final StockItemMapper stockItemMapper;
    private final ObjectMapper mapper;
    private StockEntity stockEntity;
    private StockItemRequest stockItemRequest;

    @BeforeEach
    void setUp() {
        final BrandEntity brandEntity = brandRepository.save(BrandBuilder.aBrand().build());
        final ProductEntity productEntity = productRepository.save(ProductBuilder.aProduct().withBrand(brandEntity).build());
        stockItemRequest = StockItemRequestBuilder.aStockItemRequest().withProductId(productEntity.getId()).build();
        stockEntity = stockRepository.save(stockItemMapper.toEntity(stockItemRequest));
    }

    @Test
    @WithJwt("resources/jwt/moder.json")
    @DisplayName("Tests GET /api/stock/{id} with valid ID")
    void getStockItemById_200Expected() throws Exception {
        final UUID stockItemId = stockEntity.getId();
        when(stockModeratorService.findById(stockItemId)).thenReturn(stockEntity);
        mockMvc.perform(get(EndpointsUrls.STOCK_MODER_GET_PUT_DELETE.getPath() + stockItemId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(stockItemId.toString()))
                .andExpect(jsonPath("$.color").value(stockEntity.getColor().toString()))
                .andExpect(jsonPath("$.size").value(stockEntity.getSize().toString()))
                .andExpect(jsonPath("$.amount").value(stockEntity.getAmount().intValue()))
                .andExpect(jsonPath("$.product.id").value(stockEntity.getProduct().getId().toString()))
                .andExpect(jsonPath("$.product.name").value(stockEntity.getProduct().getName()));
    }

    @Test
    @WithJwt("resources/jwt/moder.json")
    @DisplayName("Tests POST /api/stock without body")
    void createStockItemWithoutBody_400Expected() throws Exception {
        mockMvc.perform(post(EndpointsUrls.STOCK_MODER_POST.getPath())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithJwt("resources/jwt/moder.json")
    @DisplayName("Tests POST /api/stock with valid body")
    void createStockItem_201Expected() throws Exception {
        when(stockModeratorService.createStockItem(any(StockItemRequest.class))).thenReturn(stockEntity);
        mockMvc.perform(post(EndpointsUrls.STOCK_MODER_POST.getPath())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(stockItemRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(stockEntity.getId().toString()))
                .andExpect(jsonPath("$.color").value(stockEntity.getColor().toString()))
                .andExpect(jsonPath("$.size").value(stockEntity.getSize().toString()))
                .andExpect(jsonPath("$.amount").value(stockEntity.getAmount().intValue()))
                .andExpect(jsonPath("$.product.id").value(stockEntity.getProduct().getId().toString()))
                .andExpect(jsonPath("$.product.name").value(stockEntity.getProduct().getName()));    }

    @Test
    @DisplayName("Tests POST /api/stock without auth")
    void createStockItemWithoutAuth_401Expected() throws Exception {
        mockMvc.perform(post(EndpointsUrls.STOCK_MODER_POST.getPath()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithJwt("resources/jwt/moder.json")
    @DisplayName("Tests PUT /api/stock/{id} with valid body")
    void updateStockItem_200Expected() throws Exception {
        final UUID stockItemId = stockEntity.getId();
        when(stockModeratorService.updateStockItem(eq(stockItemId), any(StockItemRequest.class))).thenReturn(stockEntity);
        mockMvc.perform(put(EndpointsUrls.STOCK_MODER_GET_PUT_DELETE.getPath() + stockItemId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(stockItemRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(stockItemId.toString()))
                .andExpect(jsonPath("$.color").value(stockEntity.getColor().toString()))
                .andExpect(jsonPath("$.size").value(stockEntity.getSize().toString()))
                .andExpect(jsonPath("$.amount").value(stockEntity.getAmount().intValue()))
                .andExpect(jsonPath("$.product.id").value(stockEntity.getProduct().getId().toString()))
                .andExpect(jsonPath("$.product.name").value(stockEntity.getProduct().getName()));
    }

    @Test
    @WithJwt("resources/jwt/moder.json")
    @DisplayName("Tests DELETE /api/stock/{id} with valid ID")
    void deleteStockItem_204Expected() throws Exception {
        final UUID stockItemId = stockEntity.getId();
        doNothing().when(stockModeratorService).deleteStockItem(stockItemId);
        mockMvc.perform(delete(EndpointsUrls.STOCK_MODER_GET_PUT_DELETE.getPath() + stockItemId))
                .andExpect(status().isNoContent());
        verify(stockModeratorService, times(1)).deleteStockItem(stockItemId);
    }

    @Test
    @WithJwt("resources/jwt/moder.json")
    @DisplayName("Tests PUT /api/stock/{id} and remove all items")
    void removeStockItems_200Expected() throws Exception {
        final UUID stockItemId = stockEntity.getId();
        doNothing().when(stockModeratorService).removeStockItems(stockItemId);
        mockMvc.perform(put(EndpointsUrls.STOCK_MODER_GET_PUT_DELETE.getPath() + stockItemId + EndpointsUrls.STOCK_MODER_UTILIZATION.getPath()))
                .andExpect(status().isOk());
        verify(stockModeratorService, times(1)).removeStockItems(stockItemId);
    }
}
