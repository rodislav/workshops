package org.example.saga.coordinator.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.saga.api.Action;
import org.example.saga.api.OrderDTO;
import org.example.saga.api.PlaceOrderRequest;

import static org.example.saga.api.Action.*;

@Slf4j
@RequiredArgsConstructor
class OrderFlow {
    private final OrderDTO dto;
    private final Sender sender;
    private final Receiver receiver;

    @Getter
    private OrderDTO result;

    protected void doLock() {
        sender.send(getRequest(LOCK));
    }

    protected void doExecute() {
        sender.send(getRequest(EXECUTE));
    }

    protected void doCommit() {
        sender.send(getRequest(COMMIT));
    }

    public void doRollback() {
        sender.send(getRequest(ROLLBACK));
    }

    private PlaceOrderRequest getRequest(Action a) {
        return PlaceOrderRequest.builder()
                .customerId(dto.getCustomerId())
                .amount(dto.getAmount())
                .action(a)
                .build();
    }
}