package org.example.coordinator.domain;

import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata.Key;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.coordinator.api.CustomerDTO;
import org.example.coordinator.api.OrderDTO;
import org.example.coordinator.config.CustomerServiceParams;
import org.example.coordinator.config.OrderServiceParams;
import org.example.customer.generated.grpc.CustomerServiceGrpc;
import org.example.customer.generated.grpc.CustomerServiceGrpc.CustomerServiceBlockingStub;
import org.example.customer.generated.grpc.DebitStepRPC;
import org.example.customer.generated.grpc.DebitStepResponseRPC;
import org.example.customer.generated.grpc.TwoPCActionRPC;
import org.example.order.generated.grpc.OrderServiceGrpc;
import org.example.order.generated.grpc.OrderServiceGrpc.OrderServiceBlockingStub;
import org.example.order.generated.grpc.PlaceRPC;
import org.example.order.generated.grpc.PlaceStepRPC;
import org.example.order.generated.grpc.PlaceStepResponseRPC;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.UUID;

import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

@Component
@Slf4j
@RequiredArgsConstructor
public class GrpcClientAsync {

    private final GrpcMapper mapper;
    private final CustomerServiceParams customerServiceParams;
    private final OrderServiceParams orderServiceParams;

    OrderServiceGrpc.OrderServiceStub orderStub;
    CustomerServiceGrpc.CustomerServiceStub customerStub;

    @PostConstruct
    public void init() {
        var orderCB = ManagedChannelBuilder
                .forAddress(orderServiceParams.getHost(), orderServiceParams.getPort())
                .disableRetry()
                .usePlaintext()
                .build();

        var customerCB = ManagedChannelBuilder
                .forAddress(customerServiceParams.getHost(), customerServiceParams.getPort())
                .disableRetry()
                .usePlaintext()
                .build();

        orderStub = OrderServiceGrpc.newStub(orderCB);
        customerStub = CustomerServiceGrpc.newStub(customerCB);
    }

    private String trailersToString(StatusRuntimeException e) {
        StringBuilder details = new StringBuilder();

        if (e.getTrailers() != null) {
            final var trailers = e.getTrailers().getAll(Key.of("code", ASCII_STRING_MARSHALLER));
            if (trailers != null) {
                trailers.forEach(details::append);
            }
        }
        return details.toString();
    }

    public StreamObserver<PlaceStepRPC> init(OrderFlow flow) {
        return orderStub.placeOrderAsync(flow);
    }

    public StreamObserver<DebitStepRPC> init(CustomerFlow flow) {
        return customerStub.debitCustomerAsync(flow);
    }
}
