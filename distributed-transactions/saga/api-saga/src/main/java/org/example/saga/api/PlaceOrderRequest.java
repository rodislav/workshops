package org.example.saga.api;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

import static org.example.saga.api.Action.*;

@Setter
@Getter
@Builder
public class PlaceOrderRequest {
    private Action action;
    private OrderDTO order;

    public static PlaceOrderRequest execute(OrderDTO order) {
        return PlaceOrderRequest.builder()
                .order(order)
                .action(EXECUTE)
                .build();
    }

    public static PlaceOrderRequest error(OrderDTO order) {
        return PlaceOrderRequest.builder()
                .order(order)
                .action(ERROR)
                .build();
    }

    public static PlaceOrderRequest rollback(OrderDTO order) {
        return PlaceOrderRequest.builder()
                .order(order)
                .action(ROLLBACK)
                .build();
    }
}
