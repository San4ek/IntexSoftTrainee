package org.example.configs;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public Queue accountDeletedQueue() {
        return new Queue("account.deleted");
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
