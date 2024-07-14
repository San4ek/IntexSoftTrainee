package me.inqu1sitor.authservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import me.inqu1sitor.authservice.entities.AccountEntity;

import java.util.UUID;

/**
 * Dto for transferring the required {@link AccountEntity}
 * information from auth to user service for updating credentials.
 *
 * @author Alexander Sankevich
 */
@Schema(description = "Credentials information model for updating")
public record CredentialsTransferDto(
        @NotNull(message = "Account id is required")
        @Schema(description = "Account id", example = "c0a80067-907f-19d0-8190-7f09f40e0000")
        UUID id,
        @NotNull(message = "Email is required")
        @Email(message = "Email format required")
        @Schema(description = "Account email", example = "test@test.ru")
        String email
) {
}
