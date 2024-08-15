package org.example.services.client;

import org.example.dtos.CbrCurrencyResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Feign client for interacting with the Central Bank of Russia's currency exchange rate API.
 */
@FeignClient(name = "cbr-currency-service", url = "https://www.cbr-xml-daily.ru")
public interface CbrCurrencyClient {

    /**
     * Retrieves the daily currency exchange rates.
     *
     * @return the exchange rates for the specified date.
     */
    @GetMapping(value = "/daily.xml", produces = "application/xml;charset=windows-1251")
    @Cacheable(value = "currencyRates", unless = "#result == null or #result.isEmpty()")
    String getDailyRates();
}
