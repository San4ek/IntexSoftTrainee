package me.inquis1tor.userservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import me.inquis1tor.userservice.entities.Account;

import java.util.UUID;

@Schema(description = "Account model information")
public record AccountDto(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Account id")
        UUID id,
        @Schema(description = "Account role", ref = "RoleDto")
        RoleDto role,
        @Schema(description = "Account status", example = "ACTIVE")
        Account.Status status,
        @Schema(description = "Account credentials", ref = "CredentialsDto")
        CredentialsDto credentials,
        @Schema(description = "User personal info", ref = "PersonalInfoDto")
        PersonalInfoDto personalInfo
) {}