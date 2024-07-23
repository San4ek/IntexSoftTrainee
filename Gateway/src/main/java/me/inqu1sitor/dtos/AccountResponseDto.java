package me.inqu1sitor.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

/**
 * DTO for response with the required account information.
 *
 * @author Alexander Sankevich
 */
@Schema(description = "Account information model")
public record AccountResponseDto(
        @Schema(description = "Account id", example = "c0a80067-907f-19d0-8190-7f09f40e0000")
        UUID id,
        @Schema(description = "Account role")
        String role,
        @Schema(description = "Account status")
        String status,
        @Schema(description = "Account email", example = "test@test.ru")
        String email,
        @Schema(description = "User personal info")
        PersonalInfoDto personalInfo
) {
}
