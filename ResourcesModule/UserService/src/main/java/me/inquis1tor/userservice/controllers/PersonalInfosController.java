package me.inquis1tor.userservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.inquis1tor.userservice.annotations.swagger.parameters.AccountIdParameter;
import me.inquis1tor.userservice.annotations.swagger.requests.SwaggerRequestBody;
import me.inquis1tor.userservice.annotations.swagger.responses.BadRequestErrorResponse;
import me.inquis1tor.userservice.annotations.swagger.responses.PersonalInfoDtoOkResponse;
import me.inquis1tor.userservice.dtos.PersonalInfoDto;
import me.inquis1tor.userservice.exceptions.EndpointNotImplementedException;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Tag(name = "Personal info", description = "Personal info managements APIs")
@RequestMapping("/default/personal-infos")
public interface PersonalInfosController {

    @AccountIdParameter
    @Operation(summary = "Update user personal info by account id")
    @PersonalInfoDtoOkResponse
    @BadRequestErrorResponse
    @PutMapping
    default PersonalInfoDto updatePersonalInfo(@RequestParam UUID accountId,
                                               @SwaggerRequestBody(description = "User personal info")
                                               @RequestBody PersonalInfoDto personalInfo) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }
}
