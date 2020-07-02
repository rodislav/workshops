package com.example.securityflaws.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException(UUID id) {
        super("Not found, id: " + id.toString());
    }

    public NotFoundException(String id) {
        super("Not found, id: " + id);
    }
}
