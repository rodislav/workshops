package org.example.saga.api;

import java.io.Serializable;

public enum Action implements Serializable {
    DEBIT_CUSTOMER,
    DEBIT_CUSTOMER_OK,
    DEBIT_CUSTOMER_ERROR,
    DEBIT_CUSTOMER_ROLLBACK,
    DEBIT_CUSTOMER_ROLLBACK_OK,
    DEBIT_CUSTOMER_ROLLBACK_ERROR,
    PLACE_ORDER,
    PLACE_ORDER_ERROR,
    PLACE_ORDER_OK,
}
