package me.inqu1sitor.authservice.dtos;

import java.util.UUID;

/**
 *  The DTO for transferring mail args from services for sending.
 *
 * @author Alexander Sankevich
 */
public record SendMailRequestDto(
        UUID accountId,
        String subject,
        String body
) {
}
