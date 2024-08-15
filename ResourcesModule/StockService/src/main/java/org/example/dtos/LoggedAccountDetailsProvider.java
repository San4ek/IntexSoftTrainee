package org.example.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.RoleEnum;

import java.util.UUID;

@Data
@NoArgsConstructor
public class LoggedAccountDetailsProvider {

    private UUID accountId;

    private RoleEnum accountRole;
}
