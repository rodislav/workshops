package org.example.order;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class OrderPlacementException extends RuntimeException {

    public OrderPlacementException(Order order) {
        super("Failed to place an order, please check logs, order: " + order.toString());
    }
}
