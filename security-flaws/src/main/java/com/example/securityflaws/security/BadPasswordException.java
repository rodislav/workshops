package com.example.securityflaws.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;

public class BadPasswordException extends
        AuthenticationException {

    public BadPasswordException() {
        super("Bad password");
    }
}
