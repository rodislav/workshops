package org.example.api.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.example.domain.OrderNotValidException;
import org.example.domain.OrderPlacementException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

//https://www.baeldung.com/exception-handling-for-rest-with-spring
// https://www.baeldung.com/global-error-handler-in-a-spring-rest-api
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = OrderNotValidException.class)
    protected ResponseEntity<Object> handleBadRequestException(OrderNotValidException ex, WebRequest request) {
        return doHandle(new WebException(ex, request));
    }

    @ExceptionHandler(value = OrderPlacementException.class)
    protected ResponseEntity<Object> handleBadRequestException(OrderPlacementException ex, WebRequest request) {
        return doHandle(new WebException(ex, request));
    }

    private ResponseEntity<Object> doHandle(WebException webEx) {
        return handleExceptionInternal(webEx.getCause(), webEx, new HttpHeaders(), webEx.getHttpStatus(), webEx.getRequest());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var e = new OrderNotValidException();

        if (ex.getCause() != null && ex.getCause() instanceof InvalidFormatException) {
            var iex = (InvalidFormatException) ex.getCause();
            var fieldPath = iex.getPathReference().replace(getClass().getPackageName() + ".", "");
            e.add(new OrderNotValidException.Detail(fieldPath, iex.getOriginalMessage()));
        } else {
            e.add(new OrderNotValidException.Detail("[unknown]", ex.getMessage()));
        }

        return handleExceptionInternal(ex, e, headers, BAD_REQUEST, request);
    }

}
