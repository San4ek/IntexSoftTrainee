package me.inquis1tor.userservice.utils;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.inquis1tor.userservice.entities.AccountEntity;

import java.util.UUID;

/**
 * This class is responsible for holding the current logged {@link AccountEntity} details values.
 *
 * @author Alexander Sankevich
 */
@Data
@NoArgsConstructor
public class LoggedAccountDetailsProvider {

    /**
     * The current logged {@link AccountEntity} id.
     */
    private UUID accountId;

    /**
     * The current logged {@link AccountEntity} {@link AccountEntity.Role role}.
     */
    private AccountEntity.Role accountRole;
}
