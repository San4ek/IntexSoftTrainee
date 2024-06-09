package me.inquis1tor.userservice.dtos;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface Responsable {
    default <T> ResponseEntity<T> getDefaultResponse() {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
