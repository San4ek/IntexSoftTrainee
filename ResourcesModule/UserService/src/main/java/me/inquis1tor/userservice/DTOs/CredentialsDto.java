package me.inquis1tor.userservice.DTOs;

import jakarta.validation.constraints.Email;

public record CredentialsDto(
        @Email
        String email
) {}
