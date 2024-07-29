package org.example.schedulers;

import org.example.services.impl.CurrencyConversionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduler for clearing cache every day at 00:00
 */
@Component
public class ClearCacheScheduler {

    @Autowired
    private CurrencyConversionServiceImpl currencyConversionService;

    @Scheduled(cron = "* 0 0 * * ?")
    public void clearCache() {
        currencyConversionService.clearCache();
    }
}
