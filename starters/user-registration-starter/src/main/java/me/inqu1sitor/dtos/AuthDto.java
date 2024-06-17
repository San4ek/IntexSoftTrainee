package me.inqu1sitor.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.inqu1sitor.enums.Role;
import me.inqu1sitor.enums.Status;

import java.util.UUID;

@Data
@NoArgsConstructor
public class AuthDto {

    private UUID id;

    private String email;

    private Role role;

    private Status status;
}
