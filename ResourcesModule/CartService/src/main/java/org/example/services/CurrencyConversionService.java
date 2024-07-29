package org.example.services;

/**
 * Interface for converting currencies
 */
public interface CurrencyConversionService {

    Double convertCurrency(String from, String to, Double amount);

    void clearCache();
}
