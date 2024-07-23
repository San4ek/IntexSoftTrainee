package me.inqu1sitor.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.inqu1sitor.annotations.swagger.parameters.AccountIdParameter;
import me.inqu1sitor.annotations.swagger.responses.AccountDtoArrayOkResponse;
import me.inqu1sitor.annotations.swagger.responses.AccountDtoOkResponse;
import me.inqu1sitor.annotations.swagger.responses.BadRequestErrorResponse;
import me.inqu1sitor.annotations.swagger.responses.NoContentOkResponse;
import me.inqu1sitor.annotations.swagger.responses.NotFoundErrorResponse;
import me.inqu1sitor.annotations.swagger.security.Oauth2SecurityRequired;
import me.inqu1sitor.exceptions.EndpointNotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@Tag(name = "Account", description = "Account management APIs")
@RequestMapping("/api/accounts")
public interface AccountsController {

    @Operation(summary = "Get account info")
    @Oauth2SecurityRequired
    @AccountDtoOkResponse
    @NotFoundErrorResponse
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    default ResponseEntity<Object> getAccount() throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @Oauth2SecurityRequired
    @Operation(summary = "Get all accounts info")
    @AccountDtoArrayOkResponse
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    default ResponseEntity<Object> getAllAccounts() throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @Oauth2SecurityRequired
    @Operation(summary = "Block account by its id")
    @BadRequestErrorResponse
    @NoContentOkResponse
    @AccountIdParameter
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/block")
    default ResponseEntity<Object> blockAccount(@RequestParam("accountId") UUID accountId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @Oauth2SecurityRequired
    @BadRequestErrorResponse
    @NoContentOkResponse
    @AccountIdParameter
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/unblock")
    default ResponseEntity<Object> unblockAccount(@RequestParam("accountId") UUID accountId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @Oauth2SecurityRequired
    @NoContentOkResponse
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping
    default ResponseEntity<Object> deleteAccount() throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @Oauth2SecurityRequired
    @NoContentOkResponse
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/logout")
    default ResponseEntity<Object> logout() throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @Oauth2SecurityRequired
    @NoContentOkResponse
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/logout/all")
    default ResponseEntity<Object> logoutAll() throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }
}
