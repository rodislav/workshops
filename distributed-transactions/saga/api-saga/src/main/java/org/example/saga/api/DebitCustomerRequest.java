package org.example.saga.api;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static org.example.saga.api.Action.*;

@Setter
@Getter
@Builder
public class DebitCustomerRequest {
    private Action action;
    private OrderDTO order;

    public static DebitCustomerRequest execute(OrderDTO order) {
        return DebitCustomerRequest.builder()
                .order(order)
                .action(EXECUTE)
                .build();
    }

    public static DebitCustomerRequest error(OrderDTO order) {
        return DebitCustomerRequest.builder()
                .order(order)
                .action(ERROR)
                .build();
    }

    public static DebitCustomerRequest rollback(OrderDTO order) {
        return DebitCustomerRequest.builder()
                .order(order)
                .action(ROLLBACK)
                .build();
    }
}
