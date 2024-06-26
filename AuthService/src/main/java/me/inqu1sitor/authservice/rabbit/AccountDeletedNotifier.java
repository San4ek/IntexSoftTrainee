package me.inqu1sitor.authservice.rabbit;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AccountDeletedNotifier {

    private final RabbitTemplate rabbitTemplate;
    private final FanoutExchange fanoutExchange;

    public void notifyAbout(final UUID accountId) {
        this.rabbitTemplate.convertAndSend(fanoutExchange.getName(), "", accountId);
    }
}
