package org.example.coordinator.domain;

import org.example.coordinator.api.OrderDTO;

public class OrderPlacementException extends RuntimeException {

    public OrderPlacementException(String message, OrderDTO order) {
        super(
                String.format(
                        "Failed to place an order, message: %s, order: %s",
                        message, order.toString())
        );
    }

    public OrderPlacementException(String message) {
        super(message);
    }
}
