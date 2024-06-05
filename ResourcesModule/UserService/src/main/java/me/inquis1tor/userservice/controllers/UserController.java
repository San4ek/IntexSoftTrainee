package me.inquis1tor.userservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/user",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserController implements UserOperations {

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getUser(String email) {
        return UserOperations.super.getUser(email);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getUser(UUID id) {
        return UserOperations.super.getUser(id);
    }
}
