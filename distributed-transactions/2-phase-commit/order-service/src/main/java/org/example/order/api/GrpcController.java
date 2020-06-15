package org.example.order.api;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.customer.generated.grpc.DebitStepRPC;
import org.example.customer.generated.grpc.TwoPCActionRPC;
import org.example.order.api.dto.OrderMapper;
import org.example.order.domain.Order;
import org.example.order.domain.OrderFacade;
import org.example.order.domain.OrderPlacementException;
import org.example.order.generated.grpc.OrderServiceGrpc.OrderServiceImplBase;
import org.example.order.generated.grpc.PlaceStepRPC;
import org.example.order.generated.grpc.PlaceStepResponseRPC;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class GrpcController extends OrderServiceImplBase {

    private final OrderMapper mapper;
    private final OrderFacade facade;

    @Override
    public void placeOrder(PlaceStepRPC request, StreamObserver<PlaceStepResponseRPC> responseObserver) {
        try {
            switch (request.getAction()) {
                case LOCK:
                    // todo lock
                    responseObserver.onNext(PlaceStepResponseRPC.newBuilder().setAction(TwoPCActionRPC.LOCK).build());
                    break;

                case EXECUTE:
                    Order order = mapper.toEntity(request.getOrder());
                    facade.placeOrder(order);

                    responseObserver.onNext(PlaceStepResponseRPC.newBuilder()
                            .setAction(request.getAction())
                            .setOrder(mapper.toRPC(order))
                            .buildPartial());

                    break;

                case COMMIT:
                    // todo actual commit
                    responseObserver.onNext(PlaceStepResponseRPC.newBuilder().setAction(request.getAction()).build());
                    responseObserver.onCompleted();
                    break;

                case ROLLBACK:
                    // todo rollback
                    responseObserver.onNext(PlaceStepResponseRPC.newBuilder().setAction(request.getAction()).build());
                    responseObserver.onCompleted();
                    break;
            }
        } catch (OrderPlacementException e) {
            responseObserver.onError(new StatusRuntimeException(Status.INVALID_ARGUMENT.withDescription(e.getMessage())));
        } catch (Exception e) {
            log.error(e.getMessage());
            log.debug(e.getMessage(), e);
            responseObserver.onError(new StatusRuntimeException(Status.INTERNAL));
        }
    }

    @Override
    public StreamObserver<PlaceStepRPC> placeOrderAsync(StreamObserver<PlaceStepResponseRPC> responseObserver) {
        return new StreamObserver<>() {

            @Override
            public void onNext(PlaceStepRPC value) {
                placeOrder(value, responseObserver);
            }

            @Override
            public void onError(Throwable t) {
                log.error(t.getMessage(), t);
            }

            @Override
            public void onCompleted() {
                log.info("completed");
            }
        };
    }
}
