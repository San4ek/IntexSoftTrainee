package me.inquis1tor.userservice.dtos;

import me.inquis1tor.userservice.entities.Account;

import java.util.UUID;

public record AccountDto(UUID id,
                         RoleDto role,
                         Account.Status status,
                         CredentialsDto credentials,
                         PersonalInfoDto personalInfo) implements Responsable {}