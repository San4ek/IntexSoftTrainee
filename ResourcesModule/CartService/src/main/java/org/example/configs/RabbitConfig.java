package org.example.configs;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;

public class RabbitConfig {

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("account.deleted");
    }

    @Bean
    public Queue accountDeletedQueue() {
        return new Queue("account.deleted.queue");
    }

    @Bean
    public Binding binding(FanoutExchange fanoutExchange, Queue accountDeletedQueue) {
        return BindingBuilder.bind(accountDeletedQueue).to(fanoutExchange);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
