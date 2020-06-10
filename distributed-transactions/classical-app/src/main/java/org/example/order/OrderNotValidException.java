package org.example.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@JsonIgnoreProperties(value = {"suppressed", "localizedMessage", "stackTrace", "cause"})
public class OrderNotValidException extends RuntimeException {
    @Getter
    private List<Detail> details = new ArrayList<>();

    public OrderNotValidException() {
        super("Provided order is not valid, please check details.");
    }

    public void add(Detail d) {
        details.add(d);
    }

    public boolean hasIssues() {
        return !details.isEmpty();
    }

    @Getter
    @AllArgsConstructor
    public static class Detail {
        private String field;
        private String message;
    }
}
