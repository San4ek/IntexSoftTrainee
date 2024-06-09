package me.inquis1tor.userservice.controllers.operations;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Tag(name = "Credentials", description = "Credentials management APIs")
@RequestMapping("/default/credentials")
public interface CredentialsOperations {

    @Operation(summary = "Change account credentials")
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "501")
    @PutMapping("/update")
    default ResponseEntity<?> update(@RequestParam UUID accountId/*, @RequestBody CredentialsRequestDTO credentials*/) {
        return DefaultResponses.NOT_IMPLEMENTED_RESPONSE.getResponse();
    }
}
