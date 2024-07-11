package me.inquis1tor.userservice.rabbit.impl;

import lombok.RequiredArgsConstructor;
import me.inquis1tor.userservice.entities.AccountEntity;
import me.inquis1tor.userservice.rabbit.AccountDeletedReceiver;
import me.inquis1tor.userservice.services.AccountService;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * A RabbitMQ implementation of {@link AccountDeletedReceiver}
 * that uses {@link FanoutExchange} and {@link RabbitListener}
 * for receiving notifications.
 *
 * @author Alexander Sankevich
 */
@Component
@RequiredArgsConstructor
public class AccountDeletedReceiverImpl implements AccountDeletedReceiver {

    private final AccountService accountService;

    /**
     * Catch the notification from queue and delete the {@link AccountEntity}
     * identified with the provided parameter {@code accountId}.
     *
     * @param accountId the deleted {@link AccountEntity} id, caught from the queue
     */
    @RabbitListener(queues = "#{autoDeleteQueue.name}")
    public void receive(UUID accountId) {
        accountService.deleteAccount(accountId);
    }

}
