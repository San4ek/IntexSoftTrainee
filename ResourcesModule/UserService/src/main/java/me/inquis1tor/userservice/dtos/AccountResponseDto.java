package me.inquis1tor.userservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import me.inquis1tor.userservice.entities.Account;

import java.util.UUID;

@Schema(description = "Account information model")
public record AccountResponseDto(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Account id")
        UUID id,
        @Schema(description = "Account role", example = "USER")
        Account.Role role,
        @Schema(description = "Account status", example = "ACTIVE")
        Account.Status status,
        @Schema(description = "Account email")
        String email,
        @Schema(description = "User personal info")
        PersonalInfoDto personalInfo
) {}
