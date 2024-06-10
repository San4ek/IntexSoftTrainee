package me.inquis1tor.userservice.controllers.operations;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.inquis1tor.userservice.dtos.CredentialsAuthDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Tag(name = "Credentials", description = "Credentials management APIs")
@RequestMapping("/default/credentials")
public interface CredentialsOperations extends Responsable {

    @Parameter(name = "accountId",
            description = "Account id",
            required = true)
    @Operation(summary = "Update account credentials by account id")
    @ApiResponse(responseCode = "200",
            content = @Content)
    @ApiResponse(responseCode = "501",
            content = @Content,
            description = "Endpoint not implemented")
    @PutMapping("/update")
    default ResponseEntity<Void> update(@RequestParam UUID accountId,
                                        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Account credentials", useParameterTypeSchema = true)
                                        @RequestBody CredentialsAuthDto credentials) {
        return getDefaultResponse();
    }
}
