package me.inquis1tor.userservice.rabbit;

import lombok.RequiredArgsConstructor;
import me.inquis1tor.userservice.services.AccountService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AccountDeletedReceiver {

    private final AccountService accountService;

    @RabbitListener(queues = "#{autoDeleteQueue.name}")
    public void receive(UUID accountId) {
        accountService.delete(accountId);
    }

}
