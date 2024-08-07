package me.inqu1sitor.dto;

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
