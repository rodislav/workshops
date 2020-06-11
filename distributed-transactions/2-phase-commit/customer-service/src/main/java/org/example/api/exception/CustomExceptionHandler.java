package org.example.api.exception;

import org.example.domain.InsufficientFundsException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//https://www.baeldung.com/exception-handling-for-rest-with-spring
// https://www.baeldung.com/global-error-handler-in-a-spring-rest-api
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = InsufficientFundsException.class)
    protected ResponseEntity<Object> handleBadRequestException(InsufficientFundsException ex, WebRequest request) {
        return doHandle(new WebException(ex, request));
    }

    private ResponseEntity<Object> doHandle(WebException webEx) {
        return handleExceptionInternal(webEx.getCause(), webEx, new HttpHeaders(), webEx.getHttpStatus(), webEx.getRequest());
    }
}
