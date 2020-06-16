package org.example.saga.coordinator.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.saga.api.Action;
import org.example.saga.api.CustomerDTO;
import org.example.saga.api.DebitCustomerRequest;

import java.util.UUID;

import static org.example.saga.api.Action.EXECUTE;

@Slf4j
@RequiredArgsConstructor
class CustomerFlow {
    private final UUID customerId;
    private final long amount;
    private final Sender sender;
    private final Receiver receiver;

    @Getter
    private CustomerDTO result;

    protected void doLock() {
        sender.send(getRequest(Action.LOCK));
    }

    protected void doExecute() {
        var r = DebitCustomerRequest.builder()
                .customerId(customerId)
                .amount(amount)
                .action(EXECUTE)
                .build();

        sender.send(r);
    }

    protected void doCommit() {
        sender.send(getRequest(Action.COMMIT));
    }

    protected void doRollback() {
        sender.send(getRequest(Action.ROLLBACK));
    }

    private DebitCustomerRequest getRequest(Action rollback) {
        return DebitCustomerRequest.builder()
                .customerId(customerId)
                .action(rollback)
                .build();
    }
}