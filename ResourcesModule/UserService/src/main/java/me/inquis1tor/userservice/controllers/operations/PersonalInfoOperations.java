package me.inquis1tor.userservice.controllers.operations;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.inquis1tor.userservice.dtos.PersonalInfoDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Tag(name = "Personal info", description = "Personal info managements APIs")
@RequestMapping("/userinfo/default")
public interface PersonalInfoOperations extends Responsable {

    @Parameter(name = "accountId",
            description = "Account id",
            required = true)
    @Parameter(name = "personalInfo",
            description = "User personal info in JSON format",
            required = true,
            schema = @Schema(implementation = PersonalInfoDto.class))
    @Operation(summary = "Update user personal info by account id")
    @ApiResponse(responseCode = "200",
            content = {@Content(schema = @Schema(implementation = PersonalInfoDto.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "501",
            content = @Content(schema = @Schema()),
            description = "Endpoint not implemented")
    @PutMapping("/update")
    default ResponseEntity<PersonalInfoDto> add(@RequestParam UUID accountId, @RequestBody PersonalInfoDto personalInfo) {
        return getDefaultResponse();
    }
}
