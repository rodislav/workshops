package org.example.api;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.example.customer.InsufficientFundsException;
import org.example.order.OrderNotValidException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = InsufficientFundsException.class)
    protected ResponseEntity<Object> handleOrderNotValidException(InsufficientFundsException ex, WebRequest request) {
        final ResponseStatus annotation = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);

        return handleExceptionInternal(
                ex,
                ex,
                new HttpHeaders(),
                annotation == null ? INTERNAL_SERVER_ERROR : annotation.code(),
                request
        );
    }

    @ExceptionHandler(value = OrderNotValidException.class)
    protected ResponseEntity<Object> handleOrderNotValidException(OrderNotValidException ex, WebRequest request) {
        final ResponseStatus annotation = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);

        return handleExceptionInternal(
                ex,
                ex,
                new HttpHeaders(),
                annotation == null ? INTERNAL_SERVER_ERROR : annotation.code(),
                request
        );
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        OrderNotValidException e = new OrderNotValidException();
        if(ex.getCause() != null && ex.getCause() instanceof InvalidFormatException) {
            InvalidFormatException iex = (InvalidFormatException) ex.getCause();
            String fieldPath = iex.getPathReference().replace(getClass().getPackageName() + ".", "");
            e.add(new OrderNotValidException.Detail(fieldPath, iex.getOriginalMessage()));
        } else {
            e.add(new OrderNotValidException.Detail("[unknown]", ex.getMessage()));
        }

        return handleExceptionInternal(
                ex,
                e,
                headers,
                BAD_REQUEST,
                request
        );
    }

}
