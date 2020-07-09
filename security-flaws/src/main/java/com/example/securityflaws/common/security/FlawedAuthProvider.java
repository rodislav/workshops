package com.example.securityflaws.common.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class FlawedAuthProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {

        if(!auth.getPrincipal().equals("user")) {
            throw new BadUserNameException();
        }

        if(auth.getCredentials() != null && !auth.getCredentials().equals("password")) {
            throw new BadPasswordException();
        }

        return new UsernamePasswordAuthenticationToken(
                auth.getPrincipal(),
                auth.getCredentials(),
                List.of((GrantedAuthority) () -> "ROLE_USER"));
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}