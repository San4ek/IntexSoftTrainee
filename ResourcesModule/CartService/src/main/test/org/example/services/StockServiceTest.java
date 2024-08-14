package org.example.services;

import org.example.dtos.StockItemAmount;
import org.example.services.client.StockClient;
import org.example.services.impl.StockServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
@Testcontainers
public class StockServiceTest {

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @Autowired
    private StockServiceImpl stockService;

    @MockBean
    private StockClient stockClient;

    private StockItemAmount stockItemAmount;

    @BeforeEach
    void setUp() {
        stockItemAmount = new StockItemAmount(UUID.randomUUID(), 100L, 19.99D);
    }

    @Test
    void testGetStockItemById() {
        when(stockClient.getStockItemById(stockItemAmount.getStockItemId())).thenReturn(stockItemAmount);
        final StockItemAmount result = stockService.getStockItemById(stockItemAmount.getStockItemId());
        assertEquals(stockItemAmount, result);
        verify(stockClient, times(1)).getStockItemById(stockItemAmount.getStockItemId());
    }
}
