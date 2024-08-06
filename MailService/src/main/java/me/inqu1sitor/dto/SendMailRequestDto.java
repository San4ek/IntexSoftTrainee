package me.inqu1sitor.dto;

/**
 *  The DTO for transferring mail args from services for sending.
 *
 * @author Alexander Sankevich
 */
public record SendMailRequestDto(
        String receiver,
        String subject,
        String body
) {
}
