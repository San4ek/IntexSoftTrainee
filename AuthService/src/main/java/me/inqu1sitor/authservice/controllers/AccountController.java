package me.inqu1sitor.authservice.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import me.inqu1sitor.authservice.annotations.swagger.parameters.AccountIdParameter;
import me.inqu1sitor.authservice.annotations.swagger.requests.SwaggerRequestBody;
import me.inqu1sitor.authservice.annotations.swagger.responses.BadRequestErrorResponse;
import me.inqu1sitor.authservice.annotations.swagger.responses.ExpectationFailedErrorResponse;
import me.inqu1sitor.authservice.annotations.swagger.responses.NoContentOkResponse;
import me.inqu1sitor.authservice.annotations.swagger.security.Oauth2SecurityRequired;
import me.inqu1sitor.authservice.dtos.CredentialsRequestDto;
import me.inqu1sitor.authservice.exceptions.EndpointNotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Tag(name = "Account", description = "Account management APIs")
@RequestMapping("/api/accounts")
public interface AccountController {

    @BadRequestErrorResponse
    @ExpectationFailedErrorResponse
    @NoContentOkResponse
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/user")
    default void registerUser(@SwaggerRequestBody(description = "Account credentials") @RequestBody
                              @Valid final CredentialsRequestDto credentialsRequestDto) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Oauth2SecurityRequired
    @BadRequestErrorResponse
    @ExpectationFailedErrorResponse
    @NoContentOkResponse
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/moder")
    default void registerModer(@SwaggerRequestBody(description = "Account credentials") @RequestBody
                               @Valid final CredentialsRequestDto credentialsRequestDto) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Oauth2SecurityRequired
    @BadRequestErrorResponse
    @ExpectationFailedErrorResponse
    @NoContentOkResponse
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/admin")
    default void registerAdmin(@SwaggerRequestBody(description = "Account credentials") @RequestBody
                               @Valid final CredentialsRequestDto credentialsRequestDto) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Oauth2SecurityRequired
    @BadRequestErrorResponse
    @NoContentOkResponse
    @AccountIdParameter
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/block")
    default void blockAccount(@RequestParam("accountId") final UUID accountId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Oauth2SecurityRequired
    @BadRequestErrorResponse
    @NoContentOkResponse
    @AccountIdParameter
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/unblock")
    default void unblockAccount(@RequestParam("accountId") final UUID accountId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @Oauth2SecurityRequired
    @NoContentOkResponse
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping
    default void deleteAccount() throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @Oauth2SecurityRequired
    @BadRequestErrorResponse
    @ExpectationFailedErrorResponse
    @NoContentOkResponse
    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    default void updateAccount(@SwaggerRequestBody(description = "Account credentials")
                               @RequestBody @Valid final CredentialsRequestDto credentialsRequestDto) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @Oauth2SecurityRequired
    @NoContentOkResponse
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/logout")
    default void logout() throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @Oauth2SecurityRequired
    @NoContentOkResponse
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/logout/all")
    default void logoutAll() throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }
}
