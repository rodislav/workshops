package com.example.securityflaws.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;

public class BadUserNameException extends
        AuthenticationException {

    public BadUserNameException() {
        super("Bad username");
    }
}
