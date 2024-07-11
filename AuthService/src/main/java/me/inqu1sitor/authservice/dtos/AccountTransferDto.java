package me.inqu1sitor.authservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import me.inqu1sitor.authservice.entities.AccountEntity;

import java.util.UUID;

/**
 * DTO for transferring the required {@link AccountEntity} information from
 * auth to user service for registration.
 *
 * @author Alexander Sankevich
 */
@Schema(description = "Account transfer model")
public record AccountTransferDto(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Account id")
        UUID id,
        @Schema(description = "Account email", example = "test@test.ru")
        String email,
        @Schema(description = "Account role")
        AccountEntity.Role role
) {
}
