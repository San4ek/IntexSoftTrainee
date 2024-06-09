package me.inquis1tor.userservice.DTOs;

import me.inquis1tor.userservice.entities.Account;

import java.util.UUID;

public record AccountDTO(UUID id,
                         RoleDto role,
                         Account.Status status,
                         CredentialsDTO credentials,
                         PersonalInfoDTO personalInfo){}