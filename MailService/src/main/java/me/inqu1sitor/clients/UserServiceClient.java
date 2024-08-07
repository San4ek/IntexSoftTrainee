package me.inqu1sitor.clients;

import me.inqu1sitor.configs.ClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

/**
 * A {@link FeignClient} implementation for auth and user services conversation.
 *
 * @author Alexander Sankevich
 */
@FeignClient(
        value = "userservice",
        url = "${remote-services.user-service.url}" + "/api/accounts",
        configuration = ClientConfig.class
)
public interface UserServiceClient {

    /**
     * Sends {@code PUT /block} HTTP request for account blocking.
     *
     * @param accountId the accounts id being blocked
     */
    @GetMapping("/email")
    String getEmail(@RequestParam("accountId") UUID accountId);

}
