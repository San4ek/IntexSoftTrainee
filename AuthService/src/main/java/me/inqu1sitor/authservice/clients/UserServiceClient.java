package me.inqu1sitor.authservice.clients;

import me.inqu1sitor.authservice.configs.ClientConfig;
import me.inqu1sitor.authservice.dtos.AccountTransferDto;
import me.inqu1sitor.authservice.dtos.CredentialsTransferDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

/**
 * A {@link FeignClient} implementation for auth and user services conversation.
 *
 * @author Alexander Sankevich
 */
@FeignClient(
        value = "userservice",
        url = "http://localhost:8081/api/accounts/",
        configuration = ClientConfig.class
)
public interface UserServiceClient {

    /**
     * Sends {@code PUT /block} HTTP request for account blocking.
     *
     * @param accountId the accounts id being blocked
     * @param adminId   the admins id which blocked the account
     */
    @PutMapping("/block")
    void block(@RequestParam("accountId") final UUID accountId, @RequestParam("adminId") final UUID adminId);

    /**
     * Sends {@code PUT /unblock} HTTP request for account unblocking.
     *
     * @param accountId the accounts id being unblocked
     */
    @PutMapping("/unblock")
    void unblock(@RequestParam("accountId") final UUID accountId);

    /**
     * Sends {@code POST /} HTTP request for account registration.
     *
     * @param account the dto with required account info
     */
    @PostMapping
    void register(@RequestBody final AccountTransferDto account);

    /**
     * Sends {@code PUT /email} HTTP request for account credentials updating.
     *
     * @param credentials the dto with required account credentials info
     */
    @PutMapping("/email")
    void update(@RequestBody final CredentialsTransferDto credentials);
}
