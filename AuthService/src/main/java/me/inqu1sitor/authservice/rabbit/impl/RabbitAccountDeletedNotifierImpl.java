package me.inqu1sitor.authservice.rabbit.impl;

import lombok.RequiredArgsConstructor;
import me.inqu1sitor.authservice.entities.AccountEntity;
import me.inqu1sitor.authservice.rabbit.AccountDeletedNotifier;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * A RabbitMQ implementation of {@link AccountDeletedNotifier}
 * that uses {@link FanoutExchange} for public notification.
 *
 * @author Alexander Sankevich
 */
@Component
@RequiredArgsConstructor
public class RabbitAccountDeletedNotifierImpl implements AccountDeletedNotifier {

    private final RabbitTemplate rabbitTemplate;
    private final FanoutExchange fanoutExchange;

    /**
     * Notifies all services about deleting the account, identified by the provided parameter {@code accountId}.
     *
     * @param accountId the deleted {@link AccountEntity} id
     */
    @Override
    public void notifyAbout(final UUID accountId) {
        this.rabbitTemplate.convertAndSend(fanoutExchange.getName(), "", accountId);
    }
}
