package me.inquis1tor.userservice.DTOs;

import java.util.UUID;

public record AccountAuthDTO(UUID id,
                             RoleDto role,
                             CredentialsAuthDTO credentials) {}
