package me.inqu1sitor.authservice.utils;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.inqu1sitor.authservice.entities.Account;

import java.util.UUID;

@Data
@NoArgsConstructor
public class LoggedAccountHolder {

    private UUID id;

    private Account.Role role;
}
