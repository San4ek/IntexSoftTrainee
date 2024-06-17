package me.inquis1tor.userservice.annotations.swagger.responses;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import me.inquis1tor.userservice.dtos.AccountResponseDto;
import org.springframework.http.MediaType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

@Target(METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ApiResponse(responseCode = "200",
        content = @Content(schema = @Schema(implementation = AccountResponseDto.class),
                mediaType = MediaType.APPLICATION_JSON_VALUE))
public @interface AccountDtoOkResponse {
}
