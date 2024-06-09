package me.inquis1tor.userservice.controllers.operations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.inquis1tor.userservice.dtos.Responsable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public enum DefaultResponses {
    NOT_IMPLEMENTED_RESPONSE(new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED));

    private final ResponseEntity<Responsable> response;
}
