package me.inquis1tor.userservice.controllers.operations;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@RequestMapping("/userinfo/default")
public interface UserInfoOperations extends Responsable {

    @Operation(summary = "Update personal info by account id")
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "501")
    @PutMapping("/update")
    default ResponseEntity<?> add(@RequestParam UUID accountId/*, @RequestBody UserInfoRequestDTO userInfo*/) {
        return getDefaultResponse();
    }
}
