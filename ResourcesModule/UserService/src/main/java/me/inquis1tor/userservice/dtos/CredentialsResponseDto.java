package me.inquis1tor.userservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Credentials information model")
public record CredentialsResponseDto(
        @Schema(description = "Account email", example = "sankevich2003@mail.ru")
        String email
) {}
