package com.example.securityflaws.api.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WebException {

    @JsonIgnore
    private WebRequest request;
    @JsonIgnore
    private Exception cause;
    @JsonIgnore
    private final HttpStatus httpStatus;

    private LocalDateTime timestamp;
    private String message;
    private String path;
    private List<Object> details;

    public String getError() {
        return httpStatus.getReasonPhrase();
    }

    public int getStatus() {
        return httpStatus.value();
    }

    private void setup(Exception ex, WebRequest request) {
        timestamp = LocalDateTime.now();
        message = ex.getMessage();
        cause = ex;
        this.request = request;
        this.path = request.getDescription(false).replace("uri=", "");
    }

    public WebException(RuntimeException ex, WebRequest request) {
        setup(ex, request);
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }



}