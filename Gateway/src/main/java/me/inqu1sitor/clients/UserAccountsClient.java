package me.inqu1sitor.clients;

import me.inqu1sitor.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "useraccounts",
        url = "http://user-service:8081/api/accounts",
        configuration = FeignClientConfig.class
)
public interface UserAccountsClient {

    @GetMapping
    ResponseEntity<Object> getAccount();

    @GetMapping("/all")
    ResponseEntity<Object> getAllAccount();
}
