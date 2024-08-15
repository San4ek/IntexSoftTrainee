package me.inqu1sitor.rabbit.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.inqu1sitor.dto.SendMailRequestDto;
import me.inqu1sitor.rabbit.SendMailReceiver;
import me.inqu1sitor.services.MailSendingService;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * A RabbitMQ implementation of {@link SendMailReceiver}
 * that uses {@link FanoutExchange} and {@link RabbitListener}
 * for receiving notifications.
 *
 * @author Alexander Sankevich
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SendMailReceiverImpl implements SendMailReceiver {

    private final MailSendingService mailSendingService;

    /**
     * Catch the notification from queue and send an email with the
     * args from the provided parameter {@code dto}
     *
     * @param dto the sending email args, caught from the queue
     */
    @RabbitListener(queues = "#{autoDeleteQueue.name}")
    public void sendMail(final SendMailRequestDto dto) {
        try {
            mailSendingService.sendMail(dto);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
