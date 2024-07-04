package me.inqu1sitor.authservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(description = "Credentials information model for updating")
public record CredentialsTransferDto(
        @NotNull(message = "Account id must not be null")
        @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Account id")
        UUID accountId,
        @NotNull(message = "Email must not be null")
        @Email(message = "Email format required")
        @Schema(description = "Account email", example = "sanekvich2003@mail.ru")
        String email
){}
