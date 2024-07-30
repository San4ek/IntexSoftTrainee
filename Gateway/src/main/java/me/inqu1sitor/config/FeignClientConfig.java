package me.inqu1sitor.config;

import feign.codec.ErrorDecoder;
import me.inqu1sitor.clients.AuthAccountsClient;
import me.inqu1sitor.clients.PersonalInfoClient;
import me.inqu1sitor.clients.UserAccountsClient;
import me.inqu1sitor.controllers.handlers.impl.FeignClientErrorDecoder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link Configuration} for {@link FeignClient} support.
 *
 * @author Alexander Sankevich
 */
@Configuration
@EnableFeignClients(basePackageClasses = {
        PersonalInfoClient.class,
        UserAccountsClient.class,
        AuthAccountsClient.class
})
public class FeignClientConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignClientErrorDecoder();
    }
}
