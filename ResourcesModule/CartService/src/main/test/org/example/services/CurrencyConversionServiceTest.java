package org.example.services;

import lombok.extern.slf4j.Slf4j;
import org.example.services.client.CbrCurrencyClient;
import org.example.services.impl.CurrencyConversionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@Slf4j
@SpringBootTest
@Testcontainers
@EnableCaching
public class CurrencyConversionServiceTest {

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @MockBean
    private CbrCurrencyClient currencyClient;

    @Autowired
    private CurrencyConversionServiceImpl currencyConversionService;

    @BeforeEach
    public void setUp() {
        reset(currencyClient);
    }

    @Test
    public void testCurrencyRatesAreCached() {
        String xmlResponse = "<?xml version=\"1.0\" encoding=\"windows-1251\"?><ValCurs Date=\"13.07.2024\" name=\"Foreign Currency Market\"><Valute ID=\"R01010\"><NumCode>036</NumCode><CharCode>AUD</CharCode><Nominal>1</Nominal><Name>Австралийский доллар</Name><Value>59.4194</Value></Valute><Valute ID=\"R01235\"><NumCode>840</NumCode><CharCode>USD</CharCode><Nominal>1</Nominal><Name>Доллар США</Name><Value>87.7427</Value></Valute></ValCurs>";
        when(currencyClient.getDailyRates()).thenReturn(xmlResponse);
        Double rate1 = currencyConversionService.convertCurrency("AUD", "USD", 100.0);
        Double rate2 = currencyConversionService.convertCurrency("AUD", "USD", 100.0);
        verify(currencyClient, times(1)).getDailyRates();
        assertEquals(rate1, rate2);
    }
}
