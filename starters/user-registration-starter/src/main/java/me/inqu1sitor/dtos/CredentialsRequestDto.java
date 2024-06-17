package me.inqu1sitor.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CredentialsRequestDto {

    private String email;

    private String password;
}
