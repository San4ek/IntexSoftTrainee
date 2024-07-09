package me.inqu1sitor.authservice.configs;

import feign.Logger;
import me.inqu1sitor.authservice.clients.UserServiceClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link Configuration} for {@link FeignClient FeighClients} support.
 *
 * @author Alexander Sankevich
 */
@Configuration
@EnableFeignClients(basePackageClasses = {UserServiceClient.class})
public class ClientConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }
}
