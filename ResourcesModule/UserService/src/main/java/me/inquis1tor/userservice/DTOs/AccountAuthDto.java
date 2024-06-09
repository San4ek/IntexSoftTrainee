package me.inquis1tor.userservice.DTOs;

import java.util.UUID;

public record AccountAuthDto(UUID id,
                             RoleDto role,
                             CredentialsAuthDto credentials) {}
