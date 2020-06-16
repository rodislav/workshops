package org.example.saga.api;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import static org.example.saga.api.Action.*;

@Setter
@Getter
@Builder
public class DebitCustomerResponse implements Serializable {
    private Action action;
    private OrderDTO order;

    public static DebitCustomerResponse execute(OrderDTO order) {
        return DebitCustomerResponse.builder()
                .order(order)
                .action(EXECUTE)
                .build();
    }

    public static DebitCustomerResponse error(OrderDTO order) {
        return DebitCustomerResponse.builder()
                .order(order)
                .action(ERROR)
                .build();
    }

    public static DebitCustomerResponse rollback(OrderDTO order) {
        return DebitCustomerResponse.builder()
                .order(order)
                .action(ROLLBACK)
                .build();
    }
}
