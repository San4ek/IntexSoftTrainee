package me.inquis1tor.userservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import me.inquis1tor.userservice.entities.AccountEntity;

import java.util.UUID;

/**
 * DTO for response with the required {@link AccountEntity} information.
 *
 * @author Alexander Sankevich
 */
@Schema(description = "Account information model")
public record AccountResponseDto(
        @Schema(description = "Account id", example = "c0a80067-907f-19d0-8190-7f09f40e0000")
        UUID id,
        @Schema(description = "Account role")
        AccountEntity.Role role,
        @Schema(description = "Account status")
        AccountEntity.Status status,
        @Schema(description = "Account email", example = "test@test.ru")
        String email,
        @Schema(description = "User personal info")
        PersonalInfoDto personalInfo
) {}
