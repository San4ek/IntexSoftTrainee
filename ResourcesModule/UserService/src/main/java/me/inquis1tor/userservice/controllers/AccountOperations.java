package me.inquis1tor.userservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/default")
public interface AccountOperations {

    ResponseEntity<?> DEFAULT_RESPONSE=ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();

    @GetMapping
    default ResponseEntity<?> get(@RequestParam String email) {
        return DEFAULT_RESPONSE;
    }

    @GetMapping
    default ResponseEntity<?> get(@RequestParam UUID id) {
        return DEFAULT_RESPONSE;
    }
}
