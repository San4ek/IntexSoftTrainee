package me.inquis1tor.userservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import me.inquis1tor.userservice.entities.AccountEntity;

import java.util.UUID;

/**
 * DTO for transferring the required {@link AccountEntity} information from
 * auth to user service for registration.
 *
 * @author Alexander Sankevich
 */
@Schema(description = "Account transfer model")
public record AccountTransferDto(
        @NotNull(message = "Account id is required")
        @Schema(description = "Account id", example = "c0a80067-907f-19d0-8190-7f09f40e0000")
        UUID id,
        @NotNull(message = "Email is required")
        @Email(message = "Email format required")
        @Schema(description = "Account email", example = "test@test.ru")
        String email,
        @NotNull(message = "Role is required")
        @Schema(description = "Account role")
        AccountEntity.Role role
) {
}
