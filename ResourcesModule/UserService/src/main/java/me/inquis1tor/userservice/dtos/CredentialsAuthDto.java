package me.inquis1tor.userservice.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CredentialsAuthDto(
        @Email
        String email,

        @NotNull
        @Size(min = 6, max = 16, message = "Password length should be between 6 and 16 characters")
        String password
){}
