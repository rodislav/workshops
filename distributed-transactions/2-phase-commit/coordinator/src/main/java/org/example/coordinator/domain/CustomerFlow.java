package org.example.coordinator.domain;

import io.grpc.stub.StreamObserver;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.coordinator.api.CustomerDTO;
import org.example.customer.generated.grpc.CustomerDebitRPC;
import org.example.customer.generated.grpc.DebitStepRPC;
import org.example.customer.generated.grpc.DebitStepResponseRPC;
import org.example.customer.generated.grpc.TwoPCActionRPC;

import java.util.UUID;

import static org.example.customer.generated.grpc.TwoPCActionRPC.*;

@Slf4j
@RequiredArgsConstructor
class CustomerFlow extends FlowWithEvents implements StreamObserver<DebitStepResponseRPC> {
    private final GrpcClientAsync grpcClientAsync;
    private final GrpcMapper mapper;
    private final UUID customerId;
    private final long amount;

    @Getter
    private CustomerDTO result;
    private StreamObserver<DebitStepRPC> stepObserver;

    public void init() {
        stepObserver = grpcClientAsync.init(this);
    }

    public void lock(Pipe c) {
        onLock = c;
        final var request = CustomerDebitRPC.newBuilder()
                .setCustomerId(customerId.toString())
                .setAmount(amount)
                .build();

        var step = DebitStepRPC.newBuilder()
                .setCustomerDebit(request)
                .setAction(LOCK)
                .build();

        stepObserver.onNext(step);
    }

    public void execute(Pipe c) {
        onExecute = c;
        final var request = CustomerDebitRPC.newBuilder()
                .setCustomerId(customerId.toString())
                .setAmount(amount)
                .build();

        var step = DebitStepRPC.newBuilder()
                .setCustomerDebit(request)
                .setAction(EXECUTE)
                .build();

        stepObserver.onNext(step);
    }

    public void commit(Pipe c) {
        onCommit = c;
        stepObserver.onNext(getStep(COMMIT));
    }

    public void rollback(Pipe c) {
        onRollback = c;
        stepObserver.onNext(getStep(ROLLBACK));
    }

    private DebitStepRPC getStep(TwoPCActionRPC action) {
        return DebitStepRPC.newBuilder()
                .setAction(action)
                .build();
    }

    @Override
    public void onNext(DebitStepResponseRPC response) {
        switch (response.getAction()) {
            case EXECUTE:
                this.result = mapper.toDto(response.getCustomer());
                break;
        }

        super.trigger(response.getAction());
    }

    @Override
    public void onError(Throwable t) {
        rollback(onRollback);
        completeObserver();
    }

    private void completeObserver() {
        try {
            stepObserver.onCompleted();
        } catch (Throwable t) {
            log.error(t.getMessage());
        }
    }

    @Override
    public void onCompleted() {
        log.info("Finished debiting customer");
    }

    public void end(Pipe c) {
        completeObserver();
    }
}