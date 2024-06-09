package me.inquis1tor.userservice.dtos;

import jakarta.validation.constraints.Email;

public record CredentialsDto(
        @Email
        String email
) {}
