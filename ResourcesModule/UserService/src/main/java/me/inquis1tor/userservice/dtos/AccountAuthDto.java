package me.inquis1tor.userservice.dtos;

import java.util.UUID;

public record AccountAuthDto(UUID id,
                             RoleDto role,
                             CredentialsAuthDto credentials) {}
