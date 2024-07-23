package me.inquis1tor.userservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.inquis1tor.userservice.annotations.swagger.requests.SwaggerRequestBody;
import me.inquis1tor.userservice.annotations.swagger.responses.NoContentOkResponse;
import me.inquis1tor.userservice.annotations.swagger.security.Oauth2SecurityRequired;
import me.inquis1tor.userservice.dtos.PersonalInfoDto;
import me.inquis1tor.userservice.exceptions.EndpointNotImplementedException;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Personal info", description = "Personal info managements APIs")
@RequestMapping("/api/personal-infos")
public interface PersonalInfosController {

    @Operation(summary = "Update user personal info")
    @Oauth2SecurityRequired
    @NoContentOkResponse
    @PutMapping
    default void updatePersonalInfo(@SwaggerRequestBody(description = "User personal info")
                                    @RequestBody PersonalInfoDto personalInfo) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }
}
