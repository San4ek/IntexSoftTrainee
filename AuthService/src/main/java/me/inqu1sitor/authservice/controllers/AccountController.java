package me.inqu1sitor.authservice.controllers;

import jakarta.validation.Valid;
import me.inqu1sitor.authservice.dtos.CredentialsRequestDto;
import me.inqu1sitor.authservice.exceptions.EndpointNotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/default/accounts")
public interface AccountController {

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    default void registerUser(@Valid CredentialsRequestDto credentialsRequestDto) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PostMapping("/moder")
    @ResponseStatus(HttpStatus.OK)
    default void registerModer(@Valid CredentialsRequestDto credentialsRequestDto) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PostMapping("/admin")
    @ResponseStatus(HttpStatus.OK)
    default void registerAdmin(@Valid CredentialsRequestDto credentialsRequestDto) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PutMapping("/block")
    @ResponseStatus(HttpStatus.OK)
    default void block(UUID accountId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PutMapping("/unblock")
    @ResponseStatus(HttpStatus.OK)
    default void unblock(UUID accountId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    default void delete() throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    default void update(@Valid CredentialsRequestDto credentialsRequestDto) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }
}
