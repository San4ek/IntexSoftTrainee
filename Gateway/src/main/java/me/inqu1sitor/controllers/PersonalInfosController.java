package me.inqu1sitor.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.inqu1sitor.annotations.swagger.requests.SwaggerRequestBody;
import me.inqu1sitor.annotations.swagger.responses.NoContentOkResponse;
import me.inqu1sitor.annotations.swagger.security.Oauth2SecurityRequired;
import me.inqu1sitor.dtos.PersonalInfoDto;
import me.inqu1sitor.exceptions.EndpointNotImplementedException;
import org.springframework.http.ResponseEntity;
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
    default ResponseEntity<Object> updatePersonalInfo(@SwaggerRequestBody(description = "User personal info")
                                                      @RequestBody PersonalInfoDto personalInfo) throws EndpointNotImplementedException {
        throw new EndpointNotImplementedException();
    }
}
