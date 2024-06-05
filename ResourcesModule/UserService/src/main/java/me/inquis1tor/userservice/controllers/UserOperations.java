package me.inquis1tor.userservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@RequestMapping(value = "/default/user")
public interface UserOperations {

    ResponseEntity<?> DEFAULT_RESPONSE=ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();

    @GetMapping
    default ResponseEntity<?> getUser(@RequestParam String email) {
        return DEFAULT_RESPONSE;
    }

    @GetMapping
    default ResponseEntity<?> getUser(@RequestParam UUID id) {
        return DEFAULT_RESPONSE;
    }
}
