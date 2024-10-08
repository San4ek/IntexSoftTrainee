package me.inqu1sitor.authservice.configs;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link Configuration} for {@link RabbitTemplate RabbitMQ} support
 *
 * @author Alexander Sankevich
 */
@Configuration
public class RabbitConfig {

    @Bean
    public FanoutExchange accountDeletedFanoutExchange() {
        return new FanoutExchange("account.deleted");
    }

    @Bean
    public FanoutExchange mailSendFanoutExchange() {
        return new FanoutExchange("mail.send");
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
