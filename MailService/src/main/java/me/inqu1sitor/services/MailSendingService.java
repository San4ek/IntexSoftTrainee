package me.inqu1sitor.services;

import me.inqu1sitor.dto.SendMailRequestDto;

/**
 * Implementations of this service are responsible for sending mails.
 *
 * @author Alexander Sankevich
 */
public interface MailSendingService {

    void sendMail(SendMailRequestDto dto);
}
