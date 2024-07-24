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

    private final CartServiceImpl cartService;
    private final CartRepository cartRepository;

    @Override
    @RabbitListener(queues = "account.deleted.queue")
    public void handleAccountDeletedMessage(UUID accountId) {
        CartEntity cart = cartRepository.findByUserId(accountId);
        cartService.deleteCart(cart.getId());
    }
}
