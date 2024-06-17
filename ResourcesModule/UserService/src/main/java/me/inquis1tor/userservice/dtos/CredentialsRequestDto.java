package me.inquis1tor.userservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Credentials information model for registration")
public record CredentialsRequestDto(
        @NotNull(message = "Email must not be null")
        @Email(message = "Email format required")
        @Schema(description = "Account email", example = "sanekvich2003@mail.ru")
        String email,
        @NotNull(message = "Password must not be null")
        @Size(min = 6, max = 16, message = "Password length should be between 6 and 16 characters")
        @Schema(description = "Account password")
        String password
){}
