package com.example.securityflaws.common.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@RequiredArgsConstructor
public class FlawedAuthProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        if(!authentication.getPrincipal().equals("user")) {
            throw new BadUserNameException();
        }

        if(authentication.getCredentials() != null && !authentication.getCredentials().equals("password")) {
            throw new BadPasswordException();
        }

        return authentication;
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}