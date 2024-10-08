package me.inqu1sitor.authservice.rabbit.impl;

import lombok.RequiredArgsConstructor;
import me.inqu1sitor.authservice.entities.AccountEntity;
import me.inqu1sitor.authservice.rabbit.Notifier;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * A RabbitMQ implementation of {@link Notifier}
 * that uses {@link FanoutExchange} for public notification
 * about account deleting.
 *
 * @author Alexander Sankevich
 */
@Component
@RequiredArgsConstructor
public class RabbitAccountDeletedNotifierImpl implements Notifier<UUID> {

    private final RabbitTemplate rabbitTemplate;
    @Qualifier("accountDeletedFanoutExchange")
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
