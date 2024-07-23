package org.example.schedulers;

import org.example.services.impl.CurrencyConversionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ClearCacheScheduler {

    @Autowired
    private CurrencyConversionServiceImpl currencyConversionService;

    @Scheduled(cron = "* 0 0 * * ?")
    public void clearCache() {
        currencyConversionService.clearCache();
    }
}
