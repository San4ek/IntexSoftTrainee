package me.inquis1tor.userservice.utils;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.inquis1tor.userservice.entities.Account;

import java.util.UUID;

@Data
@NoArgsConstructor
public class LoggedAccountHolder {

    private UUID id;

    private Account.Role role;
}
