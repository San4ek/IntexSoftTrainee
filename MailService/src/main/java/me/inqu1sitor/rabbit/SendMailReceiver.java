package me.inqu1sitor.rabbit;

import me.inqu1sitor.dto.SendMailRequestDto;

/**
 * Implementations of this interface are responsible for receiving
 * notification about sending email.
 *
 * @author Alexander Sankevich
 */
public interface SendMailReceiver {

    void sendMail(SendMailRequestDto dto);
}
