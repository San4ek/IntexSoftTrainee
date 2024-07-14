package me.inquis1tor.userservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO for response on HTTP request if exception was thrown.
 *
 * @author Alexander Sankevich
 */
@Schema(description = "Error information model")
public record ErrorResponseDto(
        @Schema(description = "Error code", example = "417")
        int code,
        @Schema(description = "Error name", example = "Expectation Failed")
        String name,
        @Schema(description = "Error message", example = "Email is required")
        String message
) {
}
