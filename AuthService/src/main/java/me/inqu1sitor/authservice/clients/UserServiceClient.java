package me.inqu1sitor.authservice.clients;

import me.inqu1sitor.authservice.configs.ClientConfig;
import me.inqu1sitor.authservice.dtos.AccountDetailsTransferDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(
        value = "userservice",
        url = "http://localhost:8081/api/accounts/",
        configuration = ClientConfig.class
)
public interface UserServiceClient {

    @PutMapping("/block")
    void block(@RequestParam UUID accountId);

    @PutMapping("/unblock")
    void unblock(@RequestParam UUID accountId);

    @PostMapping
    void register(@RequestBody AccountDetailsTransferDto account);
}
