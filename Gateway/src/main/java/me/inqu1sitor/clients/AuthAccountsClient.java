package me.inqu1sitor.clients;

import me.inqu1sitor.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(
        name = "authaccounts",
        url = "http://auth-service:9000/api/accounts",
        configuration = FeignClientConfig.class
)
public interface AuthAccountsClient {

    @PutMapping("/block")
    ResponseEntity<Object> blockAccount(@RequestParam("accountId") final UUID accountId);

    @PutMapping("/unblock")
    ResponseEntity<Object> unblockAccount(@RequestParam("accountId") final UUID accountId);

    @DeleteMapping
    ResponseEntity<Object> deleteAccount();

    @PutMapping("/logout")
    ResponseEntity<Object> logout();

    @PutMapping("/logout/all")
    ResponseEntity<Object> logoutAll();
}
