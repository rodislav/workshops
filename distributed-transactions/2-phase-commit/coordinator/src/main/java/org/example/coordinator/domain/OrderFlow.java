package org.example.coordinator.domain;

import io.grpc.stub.StreamObserver;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.coordinator.api.OrderDTO;
import org.example.customer.generated.grpc.TwoPCActionRPC;
import org.example.order.generated.grpc.PlaceStepRPC;
import org.example.order.generated.grpc.PlaceStepResponseRPC;

import static org.example.customer.generated.grpc.TwoPCActionRPC.*;

@Slf4j
@RequiredArgsConstructor
class OrderFlow extends FlowWithEvents implements StreamObserver<PlaceStepResponseRPC> {
    private final OrderDTO dto;
    private final GrpcClient grpcClient;
    private final GrpcMapper mapper;

    @Getter
    private OrderDTO result;
    private StreamObserver<PlaceStepRPC> stepObserver;

    public void init() {
        stepObserver = grpcClient.init(this);
    }

    protected void doLock() {
        stepObserver.onNext(getStep(LOCK));
    }

    protected void doExecute() {
        var rpc = PlaceStepRPC.newBuilder()
                .setOrder(mapper.toRPC(dto))
                .setAction(EXECUTE)
                .build();

        stepObserver.onNext(rpc);
    }

    protected void doCommit() {
        stepObserver.onNext(getStep(COMMIT));
    }

    public void doRollback() {
        stepObserver.onNext(getStep(ROLLBACK));
    }

    private PlaceStepRPC getStep(TwoPCActionRPC rollback) {
        return PlaceStepRPC.newBuilder()
                .setAction(rollback)
                .build();
    }

    @Override
    public void onNext(PlaceStepResponseRPC response) {
        switch (response.getAction()) {
            case EXECUTE:
                this.result = mapper.toDto(response.getOrder());
                break;
            case COMMIT:
                completeObserver();
        }
        super.trigger(response.getAction());
    }

    @Override
    public void onError(Throwable t) {
        log.error(t.getMessage(), t);
        rollback(null);
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
        log.info("Finished placing order");
    }

    public void end(Pipe c) {
        completeObserver();
    }
}