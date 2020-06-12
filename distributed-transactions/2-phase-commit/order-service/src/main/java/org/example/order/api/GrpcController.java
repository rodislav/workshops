package org.example.order.api;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.example.order.api.dto.OrderMapper;
import org.example.order.domain.Order;
import org.example.order.domain.OrderFacade;
import org.example.order.generated.grpc.OrderServiceGrpc.OrderServiceImplBase;
import org.example.order.generated.grpc.OrderServiceOuterClass;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GrpcController extends OrderServiceImplBase {

    private final OrderMapper mapper;
    private final OrderFacade facade;

    @Override
    public void placeOrder(OrderServiceOuterClass.OrderRPC request, StreamObserver<OrderServiceOuterClass.OrderRPC> responseObserver) {
        try {
            Order order = mapper.toEntity(request);
            facade.placeOrder(order);

            responseObserver.onNext(mapper.toRPC(order));
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}
