package org.example.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OrderNotValidException extends RuntimeException {
    private List<Detail> details;

    public void add(Detail d) {
        if(details == null) {
            details = new ArrayList<>();
        }

        details.add(d);
    }

    public boolean hasIssues() {
        return details != null && details.size() > 0;
    }

    @AllArgsConstructor
    @Getter
    static class Detail {
        private String message;
    }
}
