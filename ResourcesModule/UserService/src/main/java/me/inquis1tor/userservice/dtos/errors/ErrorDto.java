package me.inquis1tor.userservice.dtos.errors;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Error information model")
public record ErrorDto(

        @Schema(description = "Error code", example = "1")
        int code,

        @Schema(description = "Error name", example = "Admin required")
        String name,

        @Schema(description = "Error message", example = "Administrator id is required in header")
        String message
) {}
