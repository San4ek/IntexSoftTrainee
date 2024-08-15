package org.example.rabbit.impl;

import lombok.RequiredArgsConstructor;
import org.example.entities.CartEntity;
import org.example.rabbit.AccountDeletedListener;
import org.example.repositories.CartRepository;
import org.example.services.impl.CartServiceImpl;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AccountDeletedListenerImpl implements AccountDeletedListener {

    private final CartRepository cartRepository;

    @Override
    @RabbitListener(queues = "#{accountDeletedQueue.name}")
    public void handleAccountDeletedMessage(final UUID accountId) {
        cartRepository.deleteByUserId(accountId);
    }
}
