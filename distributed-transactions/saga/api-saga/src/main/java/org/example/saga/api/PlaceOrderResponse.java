package org.example.saga.api;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PlaceOrderResponse {
    private Action action;
    private OrderDTO order;
}
