package com.example.securityflaws.api.exception;

import com.example.securityflaws.security.BadPasswordException;
import com.example.securityflaws.security.BadUserNameException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//https://www.baeldung.com/exception-handling-for-rest-with-spring
// https://www.baeldung.com/global-error-handler-in-a-spring-rest-api
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = AccessDeniedException.class)
    protected ResponseEntity<Object> handleBadPasswordException(AccessDeniedException ex, WebRequest request) {
        return doHandle(new WebException(ex, request));
    }

    @ExceptionHandler(value = InsufficientAuthenticationException.class)
    protected ResponseEntity<Object> handleBadPasswordException(InsufficientAuthenticationException ex, WebRequest request) {
        return doHandle(new WebException(ex, request));
    }

    private ResponseEntity<Object> doHandle(WebException webEx) {
        return handleExceptionInternal(webEx.getCause(), webEx, new HttpHeaders(), webEx.getHttpStatus(), webEx.getRequest());
    }
}
