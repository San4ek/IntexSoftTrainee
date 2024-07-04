package me.inqu1sitor.authservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Error information model")
public record ErrorResponseDto (
        @Schema(description = "Error code", example = "400")
        int code,
        @Schema(description = "Error name", example = "Incorrect data format")
        String name,
        @Schema(description = "Error message", example = "Email format required")
        String message
){}
