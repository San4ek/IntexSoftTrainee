package me.inquis1tor.userservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Error information model")
public record ErrorDto(
        @Schema(description = "Request parameter, caused error", example = "email")
        String parameter,
        @Schema(description = "Error message")
        String message
) {}
