package me.inquis1tor.userservice.controllers.operations;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.inquis1tor.userservice.entities.Account;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Account", description = "Account management APIs")
@RequestMapping("/default/account")
public interface AccountOperations {

    @Operation(summary = "Get account by its email")
    @ApiResponse(responseCode = "501")
    @ApiResponse(responseCode = "200")
    @GetMapping("/email/{email}")
    default ResponseEntity<?> get(@PathVariable String email) {
        return DefaultResponses.NOT_IMPLEMENTED_RESPONSE.getResponse();
    }

    @Operation(summary = "Get account by its id")
    @ApiResponse(responseCode = "501")
    @ApiResponse(responseCode = "200")
    @GetMapping("/id/{id}")
    default ResponseEntity<?> get(@PathVariable UUID id) {
        return DefaultResponses.NOT_IMPLEMENTED_RESPONSE.getResponse();
    }

    @Operation(summary = "Delete account by ints id")
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "501")
    @DeleteMapping
    default ResponseEntity<?> delete(@RequestParam UUID id) {
        return DefaultResponses.NOT_IMPLEMENTED_RESPONSE.getResponse();
    }

    @Operation(summary = "Register new account")
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "501")
    @PostMapping("/register")
    default ResponseEntity<?> register(/*@RequestBody CredentialsRequestDTO credentials*/) {
        return DefaultResponses.NOT_IMPLEMENTED_RESPONSE.getResponse();
    }

    @Operation(summary = "Get all accounts")
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "501")
    @GetMapping("/all")
    default ResponseEntity<?> getAll() {
        return DefaultResponses.NOT_IMPLEMENTED_RESPONSE.getResponse();
    }

    @Operation(summary = "Change account status by its id")
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "501")
    @PutMapping("/update")
    default ResponseEntity<?> changeStatus(@RequestParam UUID accountId, @RequestParam Account.Status status, @RequestParam UUID adminId) {
        return DefaultResponses.NOT_IMPLEMENTED_RESPONSE.getResponse();
    }
}
