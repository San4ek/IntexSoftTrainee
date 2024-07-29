package org.example.services.impl;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.dtos.CbrCurrencyResponse;
import org.example.dtos.CurrencyRateResponse;
import org.example.exceptions.InvalidObjectException;
import org.example.services.CurrencyConversionService;
import org.example.services.client.CbrCurrencyClient;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.example.utils.validation.ValidatorUtils.checkTrue;

/**
 * Service for retrieving currency rates from Central Bank of Russia in xml format
 * and converting currencies using this rates.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyConversionServiceImpl implements CurrencyConversionService {

    private final CbrCurrencyClient currencyClient;

    /**
     * Retrieves xml file from currency client in String format, convert it into dto,
     * retrieves currency rates from this dto, and converts amount into another currency.
     *
     * @param fromCurrency Currency to convert from.
     * @param toCurrency Currency to convert into.
     * @param amount Amount of currency to convert
     * @return converted amount of currency.
     */
    @SneakyThrows
    @Override
    @Cacheable("currencyRates")
    public Double convertCurrency(final String fromCurrency, final String toCurrency, final Double amount) {
        try {
            String xmlResponse = currencyClient.getDailyRates();
            xmlResponse = xmlResponse.replace(',', '.');
            XmlMapper xmlMapper = new XmlMapper();
            CbrCurrencyResponse response = xmlMapper.readValue(xmlResponse, CbrCurrencyResponse.class);
            Optional<CurrencyRateResponse> fromRate = response.getCurrencyRates().stream()
                    .filter(rate -> rate.getCharCode().equals(fromCurrency)).findFirst();
            Optional<CurrencyRateResponse> toRate = response.getCurrencyRates().stream()
                    .filter(rate -> rate.getCharCode().equals(toCurrency)).findFirst();
            checkTrue(fromRate.isPresent() && toRate.isPresent(), "Invalid currency rate");
            Double fromValue = fromRate.get().getValue() / fromRate.get().getNominal();
            Double toValue = toRate.get().getValue() / fromRate.get().getNominal();
            return amount * (fromValue / toValue);
        } catch (Exception e) {
            throw new InvalidObjectException("Failed to convert currencies.");
        }
    }

    /**
     * Method for clearing cache
     */
    @Override
    @CacheEvict("currencyRates")
    public void clearCache() {
        log.info("Cache cleared");
    }
}
