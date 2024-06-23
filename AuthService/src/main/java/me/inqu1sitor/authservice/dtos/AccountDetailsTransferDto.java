package me.inqu1sitor.authservice.dtos;

import me.inqu1sitor.authservice.entities.Account;

import java.util.UUID;

public record AccountDetailsTransferDto (

    UUID id,

    String email,

    Account.Role role
) {}
