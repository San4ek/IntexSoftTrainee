package me.inqu1sitor.authservice.configs;

import me.inqu1sitor.authservice.rabbit.AccountDeletedNotifier;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("account.deleted");
    }

    @Bean
    public AccountDeletedNotifier accountDeletedSender() {
        return new AccountDeletedNotifier();
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
