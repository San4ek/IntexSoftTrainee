package me.inquis1tor.userservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import me.inquis1tor.userservice.annotations.security.IsAdmin;
import me.inquis1tor.userservice.annotations.swagger.parameters.AccountIdParameter;
import me.inquis1tor.userservice.annotations.swagger.parameters.AdminIdParameter;
import me.inquis1tor.userservice.annotations.swagger.requests.SwaggerRequestBody;
import me.inquis1tor.userservice.annotations.swagger.responses.*;
import me.inquis1tor.userservice.annotations.swagger.statuses.OkResponseStatus;
import me.inquis1tor.userservice.dtos.AccountDetailsTransferDto;
import me.inquis1tor.userservice.dtos.AccountResponseDto;
import me.inquis1tor.userservice.dtos.CredentialsRequestDto;
import me.inquis1tor.userservice.exceptions.EndpointNotImplementedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Validated
@Tag(name = "Account", description = "Account management APIs")
@RequestMapping("/default/accounts")
public interface AccountsController {

    //works
    @AccountIdParameter
    @Operation(summary = "Get account by its id")
    @AccountDtoOkResponse
    @NotFoundErrorResponse
    @OkResponseStatus
    @GetMapping
    default @ResponseBody AccountResponseDto getAccount() throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    //works in single mode
    @PreAuthorize("@securityService.hasCode('REGISTER_CODE')")
    @Operation(summary = "Register new account")
    @NoContentOkResponse
    @ExpectationFailedErrorResponse
    @BadRequestErrorResponse
    @OkResponseStatus
    @PostMapping
    default void registerAccount(@SwaggerRequestBody(description = "Account details")
                                     @RequestBody AccountDetailsTransferDto account) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    //works
    @IsAdmin
    @Operation(summary = "Get all accounts")
    @AccountDtoArrayOkResponse
    @OkResponseStatus
    @GetMapping("/all")
    default @ResponseBody Iterable<AccountResponseDto> getAllAccounts() throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    //works in single mode
    @PreAuthorize("@securityService.hasCode('BLOCK_CODE')")
    @AccountIdParameter
    @AdminIdParameter
    @Operation(summary = "Block account by its id")
    @AccountDtoOkResponse
    @BadRequestErrorResponse
    @OkResponseStatus
    @PutMapping("/block")
    default void blockAccount(@RequestParam("accountId") UUID accountId, @RequestParam("adminId") UUID adminId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    //works in single mode
    @PreAuthorize("@securityService.hasCode('UNBLOCK_CODE')")
    @AccountIdParameter
    @Operation(summary = "Unblock account by its id")
    @AccountDtoOkResponse
    @BadRequestErrorResponse
    @OkResponseStatus
    @PutMapping("/unblock")
    default void unblockAccount(@RequestParam("accountId") UUID accountId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }


    //not implemented
    @PreAuthorize("@securityService.hasCode('EMAIL_CODE')")
    @Operation(summary = "Update account email by account id")
    @NoContentOkResponse
    @ExpectationFailedErrorResponse
    @BadRequestErrorResponse
    @PutMapping("/email")
    default void updateEmail(@SwaggerRequestBody(description = "Account credentials")
                                   @Valid @RequestBody CredentialsRequestDto credentials) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }
}
