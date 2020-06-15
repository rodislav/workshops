package org.example.coordinator.api;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

//https://www.baeldung.com/exception-handling-for-rest-with-spring
// https://www.baeldung.com/global-error-handler-in-a-spring-rest-api
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (ex.getCause() != null && ex.getCause() instanceof InvalidFormatException) {
            var iex = (InvalidFormatException) ex.getCause();
            var fieldPath = iex.getPathReference().replace(getClass().getPackageName() + ".", "");

            return handleExceptionInternal(ex, fieldPath + " " + iex.getOriginalMessage(), headers, BAD_REQUEST, request);
        }

        return handleExceptionInternal(ex, ex, headers, BAD_REQUEST, request);
    }

}
