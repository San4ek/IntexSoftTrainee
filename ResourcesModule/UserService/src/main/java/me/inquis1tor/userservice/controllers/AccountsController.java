package me.inquis1tor.userservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import me.inquis1tor.userservice.annotations.swagger.parameters.AccountIdParameter;
import me.inquis1tor.userservice.annotations.swagger.parameters.AdminIdParameter;
import me.inquis1tor.userservice.annotations.swagger.parameters.EmailParameter;
import me.inquis1tor.userservice.annotations.swagger.responses.AccountAuthDtoOkResponse;
import me.inquis1tor.userservice.annotations.swagger.responses.AccountDtoArrayOkResponse;
import me.inquis1tor.userservice.annotations.swagger.responses.AccountDtoOkResponse;
import me.inquis1tor.userservice.annotations.swagger.responses.NoContentOkResponse;
import me.inquis1tor.userservice.annotations.swagger.statuses.OkResponseStatus;
import me.inquis1tor.userservice.dtos.AccountAuthDto;
import me.inquis1tor.userservice.dtos.AccountDto;
import me.inquis1tor.userservice.dtos.CredentialsAuthDto;
import me.inquis1tor.userservice.exceptions.EndpointNotImplementedException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Validated
@Tag(name = "Account", description = "Account management APIs")
@RequestMapping("/default/accounts")
public interface AccountsController {

    @EmailParameter
    @Operation(summary = "Get account by its email")
    @AccountAuthDtoOkResponse
    @OkResponseStatus
    @GetMapping
    default @ResponseBody AccountAuthDto getAccount(@RequestParam
                                                 @Email(message = "Email format required")
                                                 String email) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @AccountIdParameter
    @Operation(summary = "Get account by its id")
    @AccountDtoOkResponse
    @OkResponseStatus
    @GetMapping("/auth")
    default @ResponseBody AccountDto getAccount(@RequestParam UUID accountId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @AccountIdParameter
    @Operation(summary = "Delete account by ints id")
    @NoContentOkResponse
    @OkResponseStatus
    @DeleteMapping
    default void deleteAccount(@RequestParam UUID accountId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @Operation(summary = "Register new account")
    @NoContentOkResponse
    @OkResponseStatus
    @PostMapping
    default void registerAccount(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Account credentials",
                    useParameterTypeSchema = true)
            @Valid @RequestBody CredentialsAuthDto credentials) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @Operation(summary = "Get all accounts")
    @AccountDtoArrayOkResponse
    @OkResponseStatus
    @GetMapping("/all")
    default @ResponseBody Iterable<AccountDto> getAllAccounts() throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @AccountIdParameter
    @AdminIdParameter
    @Operation(summary = "Block account by its id")
    @AccountDtoOkResponse
    @OkResponseStatus
    @PutMapping("/block")
    default @ResponseBody AccountDto blockAccount(@RequestParam UUID accountId, @RequestParam UUID adminId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @AccountIdParameter
    @AdminIdParameter
    @Operation(summary = "Unblock account by its id")
    @AccountDtoOkResponse
    @OkResponseStatus
    @PutMapping("/unblock")
    default @ResponseBody AccountDto unblockAccount(@RequestParam UUID accountId, @RequestParam UUID adminId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }
}
