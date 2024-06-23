package me.inquis1tor.userservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import me.inquis1tor.userservice.annotations.swagger.parameters.AccountIdParameter;
import me.inquis1tor.userservice.annotations.swagger.parameters.AdminIdParameter;
import me.inquis1tor.userservice.annotations.swagger.parameters.EmailParameter;
import me.inquis1tor.userservice.annotations.swagger.requests.SwaggerRequestBody;
import me.inquis1tor.userservice.annotations.swagger.responses.*;
import me.inquis1tor.userservice.annotations.swagger.statuses.OkResponseStatus;
import me.inquis1tor.userservice.dtos.AccountAuthResponseDto;
import me.inquis1tor.userservice.dtos.AccountResponseDto;
import me.inquis1tor.userservice.dtos.CredentialsRequestDto;
import me.inquis1tor.userservice.exceptions.EndpointNotImplementedException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Validated
@Tag(name = "Account", description = "Account management APIs")
@RequestMapping("/default/accounts")
public interface AccountsController {

    @EmailParameter
    @Operation(summary = "Check account existence by its email")
    @AccountAuthDtoOkResponse
    @ExpectationFailedErrorResponse
    @NotFoundErrorResponse
    @OkResponseStatus
    @GetMapping
    default @ResponseBody AccountAuthResponseDto getAccount(@RequestParam @Email(message = "Email format required")
                                                                String email) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @AccountIdParameter
    @Operation(summary = "Get account by its id")
    @AccountDtoOkResponse
    @NotFoundErrorResponse
    @OkResponseStatus
    @GetMapping("/auth")
    default @ResponseBody AccountResponseDto getAccount(@RequestParam UUID accountId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @AccountIdParameter
    @Operation(summary = "Delete account by its id")
    @NoContentOkResponse
    @BadRequestErrorResponse
    @OkResponseStatus
    @DeleteMapping
    default void deleteAccount(@RequestParam UUID accountId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @Operation(summary = "Register new account")
    @NoContentOkResponse
    @ExpectationFailedErrorResponse
    @BadRequestErrorResponse
    @OkResponseStatus
    @PostMapping
    default void registerAccount(@SwaggerRequestBody(description = "Account credentials")
                                     @Valid @RequestBody CredentialsRequestDto credentials) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @Operation(summary = "Get all accounts")
    @AccountDtoArrayOkResponse
    @OkResponseStatus
    @GetMapping("/all")
    default @ResponseBody Iterable<AccountResponseDto> getAllAccounts() throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @AccountIdParameter
    @AdminIdParameter
    @Operation(summary = "Block account by its id")
    @AccountDtoOkResponse
    @BadRequestErrorResponse
    @OkResponseStatus
    @PutMapping("/block")
    default @ResponseBody AccountResponseDto blockAccount(@RequestParam UUID accountId, @RequestParam UUID adminId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @AccountIdParameter
    @AdminIdParameter
    @Operation(summary = "Unblock account by its id")
    @AccountDtoOkResponse
    @BadRequestErrorResponse
    @OkResponseStatus
    @PutMapping("/unblock")
    default @ResponseBody AccountResponseDto unblockAccount(@RequestParam UUID accountId, @RequestParam UUID adminId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }
}
