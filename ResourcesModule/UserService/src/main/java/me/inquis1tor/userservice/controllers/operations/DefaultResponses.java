package me.inquis1tor.userservice.controllers.operations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@AllArgsConstructor
public enum DefaultResponses {
    NOT_IMPLEMENTED_RESPONSE(new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED));

    private final ResponseEntity<?> response;
}
