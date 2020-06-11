package org.example.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class OrderNotValidException extends RuntimeException {
    @Getter
    private List<Detail> details = new ArrayList<>();

    public OrderNotValidException() {
        super("Provided order is not valid.");
    }

    public OrderNotValidException(Detail d) {
        super("Provided order is not valid.");
        add(d);
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
