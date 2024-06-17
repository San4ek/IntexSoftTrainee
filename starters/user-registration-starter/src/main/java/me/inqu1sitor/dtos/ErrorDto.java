package me.inqu1sitor.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Error information model")
public class ErrorDto {

        @Schema(description = "Error name", example = "Incorrect data format")
        private String name;

        @Schema(description = "Error message", example = "Email format required")
        private String message;
}
