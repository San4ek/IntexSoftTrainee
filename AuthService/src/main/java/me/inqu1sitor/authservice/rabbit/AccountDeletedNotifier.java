package me.inqu1sitor.authservice.rabbit;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
public class AccountDeletedNotifier {

    private RabbitTemplate rabbitTemplate;
    private FanoutExchange fanoutExchange;

    public void notifyAbout(final UUID accountId) {
        this.rabbitTemplate.convertAndSend(fanoutExchange.getName(), "",accountId);
    }
}
