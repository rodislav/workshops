package com.example.securityflaws.common.security;

import org.springframework.security.core.AuthenticationException;

public class BadUserNameException extends
        AuthenticationException {

    public BadUserNameException() {
        super("Bad username");
    }
}
