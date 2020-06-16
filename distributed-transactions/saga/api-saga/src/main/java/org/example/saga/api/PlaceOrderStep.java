package org.example.saga.api;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import static org.example.saga.api.Action.*;

@Setter
@Getter
@Builder
public class PlaceOrderStep implements Serializable {
    private Action action;
    private OrderDTO order;

    public static PlaceOrderStep debitCustomer(OrderDTO order) {
        return buildStep(DEBIT_CUSTOMER, order);
    }

    public static PlaceOrderStep debitCustomerError(OrderDTO order) {
        return buildStep(DEBIT_CUSTOMER_ERROR, order);
    }

    public static PlaceOrderStep debitCustomerOk(OrderDTO order) {
        return buildStep(DEBIT_CUSTOMER_OK, order);
    }

    public static PlaceOrderStep debitCustomerRollback(OrderDTO order) {
        return buildStep(DEBIT_CUSTOMER_ROLLBACK, order);
    }

    public static PlaceOrderStep debitCustomerRollbackOk(OrderDTO order) {
        return buildStep(DEBIT_CUSTOMER_ROLLBACK_OK, order);
    }

    public static PlaceOrderStep debitCustomerRollbackError(OrderDTO order) {
        return buildStep(DEBIT_CUSTOMER_ROLLBACK_ERROR, order);
    }

    public static PlaceOrderStep placeOrder(OrderDTO order) {
        return buildStep(PLACE_ORDER, order);
    }

    public static PlaceOrderStep placeOrderError(OrderDTO order) {
        return buildStep(PLACE_ORDER_ERROR, order);
    }

    public static PlaceOrderStep placeOrderOk(OrderDTO order) {
        return buildStep(PLACE_ORDER_OK, order);
    }

    private static PlaceOrderStep buildStep(Action a, OrderDTO order) {
        return PlaceOrderStep.builder()
                .order(order)
                .action(a)
                .build();
    }
}
