package me.inqu1sitor.authservice.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.inqu1sitor.authservice.enums.Role;

import java.util.UUID;

@Data
@NoArgsConstructor
public class AccountDetailsTransferDto {

    private UUID id;

    private String email;

    private Role role;
}
