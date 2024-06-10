package me.inquis1tor.userservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Personal info information model")
public record PersonalInfoDto(
        @Schema(description = "User name", example = "Alexander")
        String name,
        @Schema(description = "User surname", example = "Vadimovich")
        String surname,
        @Schema(description = "User patronymic", example = "Vadimovich")
        String patronymic,
        @Schema(description = "User phone number", example = "+375298015063")
        String phoneNumber
) {}
