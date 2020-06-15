package org.example.order.api;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
                    break;
                case EXECUTE:
                    Order order = mapper.toEntity(request.getOrder());
                    facade.placeOrder(order);

                    responseObserver.onNext(PlaceStepResponseRPC.newBuilder()
                            .setOrder(mapper.toRPC(order))
                            .buildPartial());

                    break;
                case COMMIT:
                    // todo actual commit
                    responseObserver.onCompleted();
                    break;
                case ROLLBACK:
                    // todo rollback
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
}
