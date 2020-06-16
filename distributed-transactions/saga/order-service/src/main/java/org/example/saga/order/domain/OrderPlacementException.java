package org.example.saga.order.domain;

import org.springframework.transaction.TransactionException;

public class OrderPlacementException extends RuntimeException {

    public OrderPlacementException(Order order, TransactionException e) {
        super("Failed to place an order, order: " + order.toString(), e);
    }

    public OrderPlacementException(Order order) {
        super("Failed to place an order, unknown customer, order: " + order.toString());
    }
}
