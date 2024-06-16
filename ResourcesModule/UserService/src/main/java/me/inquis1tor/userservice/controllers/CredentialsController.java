package me.inquis1tor.userservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import me.inquis1tor.userservice.annotations.swagger.parameters.AccountIdParameter;
import me.inquis1tor.userservice.annotations.swagger.responses.NoContentOkResponse;
import me.inquis1tor.userservice.dtos.CredentialsAuthDto;
import me.inquis1tor.userservice.exceptions.EndpointNotImplementedException;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Tag(name = "Credentials", description = "Credentials management APIs")
@RequestMapping("/default/credentials")
public interface CredentialsController {

    @AccountIdParameter
    @Operation(summary = "Update account credentials by account id")
    @NoContentOkResponse
    @PutMapping
    default void updateCredentials(@RequestParam UUID accountId,
                                   @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Account credentials", useParameterTypeSchema = true)
                                        @Valid @RequestBody CredentialsAuthDto credentials) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }
}
