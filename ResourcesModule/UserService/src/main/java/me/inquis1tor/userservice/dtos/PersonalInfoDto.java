package me.inquis1tor.userservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO for response with the required {@link PersonalInfoDto} information.
 *
 * @author Alexander Sankevich
 */
@Schema(description = "Personal info information model")
public record PersonalInfoDto(
        @Schema(description = "User name", example = "test")
        String name,
        @Schema(description = "User surname", example = "test")
        String surname,
        @Schema(description = "User patronymic", example = "test")
        String patronymic,
        @Schema(description = "User phone number", example = "+0000000000000")
        String phoneNumber
) {}
