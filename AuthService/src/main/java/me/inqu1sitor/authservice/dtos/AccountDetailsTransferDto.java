package me.inqu1sitor.authservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import me.inqu1sitor.authservice.entities.AccountEntity;

import java.util.UUID;

@Schema(description = "Account transfer model")
public record AccountDetailsTransferDto(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Account id")
        UUID id,
        @Schema(description = "Account email", example = "sankevich2003@mail.ru")
        String email,
        @Schema(description = "Account role")
        AccountEntity.Role role
) {}
