package com.example.securityflaws.common.security;

import org.springframework.security.core.AuthenticationException;

public class BadPasswordException extends
        AuthenticationException {

    public BadPasswordException() {
        super("Bad password");
    }
}
