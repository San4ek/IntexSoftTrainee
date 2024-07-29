package org.example.utils.security;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class LoggedAccountDetailsProvider {

    private UUID accountId;

    private RoleEnum accountRole;
}
