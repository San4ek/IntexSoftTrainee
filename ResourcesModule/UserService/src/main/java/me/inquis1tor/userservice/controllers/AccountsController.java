package me.inquis1tor.userservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import me.inquis1tor.userservice.annotations.swagger.parameters.AccountIdParameter;
import me.inquis1tor.userservice.annotations.swagger.parameters.AdminIdParameter;
import me.inquis1tor.userservice.annotations.swagger.requests.SwaggerRequestBody;
import me.inquis1tor.userservice.annotations.swagger.responses.AccountDtoArrayOkResponse;
import me.inquis1tor.userservice.annotations.swagger.responses.AccountDtoOkResponse;
import me.inquis1tor.userservice.annotations.swagger.responses.BadRequestErrorResponse;
import me.inquis1tor.userservice.annotations.swagger.responses.NoContentOkResponse;
import me.inquis1tor.userservice.annotations.swagger.responses.NotFoundErrorResponse;
import me.inquis1tor.userservice.annotations.swagger.security.Oauth2SecurityRequired;
import me.inquis1tor.userservice.dtos.AccountResponseDto;
import me.inquis1tor.userservice.dtos.AccountTransferDto;
import me.inquis1tor.userservice.dtos.CredentialsTransferDto;
import me.inquis1tor.userservice.exceptions.EndpointNotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
    default @ResponseBody AccountResponseDto getAccount() throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @Operation(summary = "Get account email by its id")
    @NotFoundErrorResponse
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/email")
    default @ResponseBody String getEmail(@RequestParam("accountId") UUID accountId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }


    @Operation(summary = "Register new account")
    @NoContentOkResponse
    @BadRequestErrorResponse
    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    default void registerAccount(@SwaggerRequestBody(description = "Account details")
                                 @Valid @RequestBody AccountTransferDto dto) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @Oauth2SecurityRequired
    @Operation(summary = "Get all accounts info")
    @AccountDtoArrayOkResponse
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    default @ResponseBody Iterable<AccountResponseDto> getAllAccounts() throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @AccountIdParameter
    @AdminIdParameter
    @Operation(summary = "Block account by its id")
    @AccountDtoOkResponse
    @BadRequestErrorResponse
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/block")
    default void blockAccount(@RequestParam("accountId") UUID accountId, @RequestParam("adminId") UUID adminId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @AccountIdParameter
    @Operation(summary = "Unblock account by its id")
    @AccountDtoOkResponse
    @BadRequestErrorResponse
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/unblock")
    default void unblockAccount(@RequestParam("accountId") UUID accountId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @Operation(summary = "Update account email")
    @NoContentOkResponse
    @BadRequestErrorResponse
    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    default void updateCredentials(@SwaggerRequestBody(description = "Account credentials")
                                   @Valid @RequestBody CredentialsTransferDto dto) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }
}
