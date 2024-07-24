package org.example.rabbit;

import java.util.UUID;

public interface AccountDeletedListener {

    void handleAccountDeletedMessage(UUID accountId);
}
