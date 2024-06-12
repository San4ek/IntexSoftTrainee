package me.inquis1tor.userservice.controllers.operations;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import me.inquis1tor.userservice.dtos.AccountAuthDto;
import me.inquis1tor.userservice.dtos.AccountDto;
import me.inquis1tor.userservice.dtos.CredentialsAuthDto;
import me.inquis1tor.userservice.exceptions.EndpointNotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Account", description = "Account management APIs")
@RequestMapping("/default/account")
public interface AccountOperations extends Responsable {

    @SneakyThrows
    @Parameter(name ="email",
            description = "Account email",
            required = true)
    @Operation(summary = "Get account by its email")
    @ApiResponse(responseCode = "501",
            content = @Content,
            description = "Endpoint not implemented")
    @ApiResponse(responseCode = "200",
            content = @Content(schema = @Schema(implementation = AccountAuthDto.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    default @ResponseBody AccountAuthDto get(@RequestParam String email) {
        throw new EndpointNotImplementedException();
    }

    @SneakyThrows
    @Parameter(name = "accountId",
            description = "Account id",
            required = true)
    @Operation(summary = "Get account by its id")
    @ApiResponse(responseCode = "501",
            content = @Content,
            description = "Endpoint not implemented")
    @ApiResponse(responseCode = "200",
            content = @Content(schema = @Schema(implementation = AccountDto.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/auth")
    default @ResponseBody AccountDto get(@RequestParam UUID accountId) {
        throw new EndpointNotImplementedException();
    }

    @SneakyThrows
    @Parameter(name = "id",
            description = "Account id",
            required = true)
    @Operation(summary = "Delete account by ints id")
    @ApiResponse(responseCode = "200",
            content = @Content)
    @ApiResponse(responseCode = "501",
            content = @Content,
            description = "Endpoint not implemented")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping
    default void delete(@RequestParam UUID id) {
        throw new EndpointNotImplementedException();
    }

    @SneakyThrows
    @Operation(summary = "Register new account")
    @ApiResponse(responseCode = "200",
            content = @Content)
    @ApiResponse(responseCode = "501",
            content = @Content,
            description = "Endpoint not implemented")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/register")
    default void register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Account credentials",
                    useParameterTypeSchema = true)
            @Valid @RequestBody CredentialsAuthDto credentials) {
        throw new EndpointNotImplementedException();
    }

    @SneakyThrows
    @Operation(summary = "Get all accounts")
    @ApiResponse(responseCode = "200",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = AccountDto.class)),
                            mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "501",
            content = @Content,
            description = "Endpoint not implemented")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    default @ResponseBody Iterable<AccountDto> getAll() {
        throw new EndpointNotImplementedException();
    }

    @SneakyThrows
    @Parameter(name = "accountId",
            description = "Account id",
            required = true)
    @Parameter(name = "Administrator",
            in = ParameterIn.HEADER,
            description = "Administrator id",
            schema = @Schema(implementation = UUID.class),
            required = true)
    @Operation(summary = "Block account by its id")
    @ApiResponse(responseCode = "200",
            content = @Content(schema = @Schema(implementation = AccountDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "501",
            content = @Content,
            description = "Endpoint not implemented")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/block")
    default @ResponseBody AccountDto block(@RequestParam UUID accountId) {
        throw new EndpointNotImplementedException();
    }

    @SneakyThrows
    @Parameter(name = "accountId",
            description = "Account id",
            required = true)
    @Parameter(name = "Administrator",
            in = ParameterIn.HEADER,
            description = "Administrator id",
            schema = @Schema(implementation = UUID.class),
            required = true)
    @Operation(summary = "Unblock account by its id")
    @ApiResponse(responseCode = "200",
            content = @Content(schema = @Schema(implementation = AccountDto.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "501",
            content = @Content,
            description = "Endpoint not implemented")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/unblock")
    default @ResponseBody AccountDto unblock(@RequestParam UUID accountId) {
        throw new EndpointNotImplementedException();
    }
}
