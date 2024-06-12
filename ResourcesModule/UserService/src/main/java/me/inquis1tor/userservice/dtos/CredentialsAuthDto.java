package me.inquis1tor.userservice.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CredentialsAuthDto(
        @NotNull(message = "Email must not be null")
        @Email (message = "Email format required")
        String email,

        @NotNull(message = "Password must not be null")
        @Size(min = 6, max = 16, message = "Password length should be between 6 and 16 characters")
        String password
){}
