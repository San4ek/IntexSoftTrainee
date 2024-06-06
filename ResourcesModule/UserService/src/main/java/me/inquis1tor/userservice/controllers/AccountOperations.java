package me.inquis1tor.userservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Account", description = "Account management APIs")
@RequestMapping("/default")
public interface AccountOperations {

    ResponseEntity<?> DEFAULT_RESPONSE=ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();

    @Operation(summary = "Get account by its email")
    @ApiResponse(responseCode = "501")
    @ApiResponse(responseCode = "200")
    @GetMapping("/email/{email}")
    default ResponseEntity<?> get(@PathVariable String email) {
        return DEFAULT_RESPONSE;
    }

    @Operation(summary = "Get account by its id")
    @ApiResponse(responseCode = "501")
    @ApiResponse(responseCode = "200")
    @GetMapping("/id/{id}")
    default ResponseEntity<?> get(@PathVariable UUID id) {
        return DEFAULT_RESPONSE;
    }
}
