package org.example.saga.api;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import static org.example.saga.api.Action.*;

@Setter
@Getter
@Builder
public class PlaceOrderResponse implements Serializable {
    private Action action;
    private OrderDTO order;

    public static PlaceOrderResponse execute(OrderDTO order) {
        return PlaceOrderResponse.builder()
                .order(order)
                .action(EXECUTE)
                .build();
    }

    public static PlaceOrderResponse error(OrderDTO order) {
        return PlaceOrderResponse.builder()
                .order(order)
                .action(ERROR)
                .build();
    }

    public static PlaceOrderResponse rollback(OrderDTO order) {
        return PlaceOrderResponse.builder()
                .order(order)
                .action(ROLLBACK)
                .build();
    }
}
