package me.inquis1tor.userservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Error information model")
public record ErrorResponseDto(
        @Schema(description = "Error name", example = "Incorrect data format")
        String name,
        @Schema(description = "Error message", example = "Email format required")
        String message
) {}
