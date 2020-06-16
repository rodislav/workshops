package org.example.saga.api;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Builder
public class PlaceOrderRequest {
    private Action action;
    private UUID customerId;
    private long amount;
}
