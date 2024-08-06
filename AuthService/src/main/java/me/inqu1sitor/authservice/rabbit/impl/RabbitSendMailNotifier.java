package me.inqu1sitor.authservice.rabbit.impl;

import lombok.RequiredArgsConstructor;
import me.inqu1sitor.authservice.dtos.SendMailRequestDto;
import me.inqu1sitor.authservice.rabbit.Notifier;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * A RabbitMQ implementation of {@link Notifier}
 * that uses {@link FanoutExchange} for public notification
 * about sending email.
 *
 * @author Alexander Sankevich
 */
@Component
@RequiredArgsConstructor
public class RabbitSendMailNotifier implements Notifier<SendMailRequestDto> {

    private final RabbitTemplate rabbitTemplate;
    @Qualifier("mailSendFanoutExchange")
    private final FanoutExchange fanoutExchange;

    /**
     * Notifies all services about sending an email with args,
     * provided by the parameter {@code dto}.
     *
     * @param dto the sending email args
     */
    @Override
    public void notifyAbout(final SendMailRequestDto dto) {
        this.rabbitTemplate.convertAndSend(fanoutExchange.getName(), "", dto);
    }
}
