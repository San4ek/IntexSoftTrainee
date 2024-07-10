package me.inqu1sitor.authservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO for new account registration or updating existed with new credentials.
 *
 * @author Alexander Sankevich
 */
@Schema(description = "Credentials information model for registration")
public record CredentialsRequestDto(
        @Email(message = "Email format required")
        @NotNull(message = "Email is required")
        @Schema(description = "Account email", example = "test@test.ru")
        String email,
        @NotNull(message = "Password is required")
        @Size(min = 6, max = 16, message = "Password should be between 6 and 16 characters")
        @Schema(description = "Account password", example = "123456")
        String password
) {
}
