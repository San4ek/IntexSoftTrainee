package me.inquis1tor.userservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Account tole information")
public record RoleDto(
        @Schema(description = "Role title", example = "user")
        String title
) {}
