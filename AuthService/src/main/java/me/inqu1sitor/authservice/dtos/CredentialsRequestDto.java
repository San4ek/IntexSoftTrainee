package me.inqu1sitor.authservice.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CredentialsRequestDto(
        @Email(message = "Email format required")
        @NotNull(message = "Email is required")
        String email,
        @NotNull(message = "Password is required")
        @Size(min = 6, max = 16, message = "Password should be between 6 and 16 characters")
        String password
) {}
