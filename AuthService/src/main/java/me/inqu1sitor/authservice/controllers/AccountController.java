package me.inqu1sitor.authservice.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import me.inqu1sitor.authservice.annotations.security.IsAdmin;
import me.inqu1sitor.authservice.annotations.swagger.parameters.AccountIdParameter;
import me.inqu1sitor.authservice.annotations.swagger.requests.SwaggerRequestBody;
import me.inqu1sitor.authservice.annotations.swagger.responses.BadRequestErrorResponse;
import me.inqu1sitor.authservice.annotations.swagger.responses.ExpectationFailedErrorResponse;
import me.inqu1sitor.authservice.annotations.swagger.responses.NoContentOkResponse;
import me.inqu1sitor.authservice.annotations.swagger.security.Oauth2SecurityRequired;
import me.inqu1sitor.authservice.annotations.swagger.statuses.OkResponseStatus;
import me.inqu1sitor.authservice.dtos.CredentialsRequestDto;
import me.inqu1sitor.authservice.exceptions.EndpointNotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Tag(name = "Account", description = "Account management APIs")
@RequestMapping("/default/accounts")
public interface AccountController {

    @BadRequestErrorResponse
    @ExpectationFailedErrorResponse
    @NoContentOkResponse
    @PostMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    default void registerUser(@SwaggerRequestBody(description = "Account credentials") @RequestBody
                                  @Valid final CredentialsRequestDto credentialsRequestDto) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @IsAdmin
    @Oauth2SecurityRequired
    @BadRequestErrorResponse
    @ExpectationFailedErrorResponse
    @NoContentOkResponse
    @PostMapping("/moder")
    @OkResponseStatus
    default void registerModer(@SwaggerRequestBody(description = "Account credentials") @RequestBody
                                   @Valid final CredentialsRequestDto credentialsRequestDto) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @IsAdmin
    @Oauth2SecurityRequired
    @BadRequestErrorResponse
    @ExpectationFailedErrorResponse
    @NoContentOkResponse
    @PostMapping("/admin")
    @OkResponseStatus
    default void registerAdmin(@SwaggerRequestBody(description = "Account credentials") @RequestBody
                                   @Valid final CredentialsRequestDto credentialsRequestDto) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @IsAdmin
    @Oauth2SecurityRequired
    @BadRequestErrorResponse
    @NoContentOkResponse
    @AccountIdParameter
    @PutMapping("/block")
    @OkResponseStatus
    default void blockAccount(@RequestParam("accountId") final UUID accountId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @IsAdmin
    @Oauth2SecurityRequired
    @BadRequestErrorResponse
    @NoContentOkResponse
    @AccountIdParameter
    @PutMapping("/unblock")
    @OkResponseStatus
    default void unblockAccount(@RequestParam("accountId") final UUID accountId) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @Oauth2SecurityRequired
    @NoContentOkResponse
    @DeleteMapping
    @OkResponseStatus
    default void deleteAccount() throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }

    @Oauth2SecurityRequired
    @BadRequestErrorResponse
    @ExpectationFailedErrorResponse
    @NoContentOkResponse
    @PutMapping
    @OkResponseStatus
    default void updateAccount(@SwaggerRequestBody(description = "Account credentials")
                            @RequestBody @Valid final CredentialsRequestDto credentialsRequestDto) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }
}
