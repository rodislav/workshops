package org.example.coordinator.domain;

import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata.Key;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.coordinator.api.CustomerDTO;
import org.example.coordinator.api.OrderDTO;
import org.example.coordinator.config.CustomerServiceParams;
import org.example.coordinator.config.OrderServiceParams;
import org.example.customer.generated.grpc.CustomerServiceGrpc;
import org.example.customer.generated.grpc.CustomerServiceGrpc.CustomerServiceBlockingStub;
import org.example.customer.generated.grpc.DebitStepRPC;
import org.example.customer.generated.grpc.TwoPCActionRPC;
import org.example.order.generated.grpc.OrderServiceGrpc;
import org.example.order.generated.grpc.OrderServiceGrpc.OrderServiceBlockingStub;
import org.example.order.generated.grpc.PlaceStepRPC;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

@Component
@Slf4j
@RequiredArgsConstructor
public class GrpcClient {

    private final GrpcMapper mapper;
    private final CustomerServiceParams customerServiceParams;
    private final OrderServiceParams orderServiceParams;

    OrderServiceBlockingStub orderStub;
    CustomerServiceBlockingStub customerStub;

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

        orderStub = OrderServiceGrpc.newBlockingStub(orderCB);
        customerStub = CustomerServiceGrpc.newBlockingStub(customerCB);
    }

    public CustomerDTO lockCustomerDebit(UUID customerId) {
        final var responseRPC = customerStub.debitCustomer(DebitStepRPC.newBuilder()
                .setAction(TwoPCActionRPC.LOCK)
                .setCustomerDebit(mapper.toRPC(customerId, 0L))
                .build());

        return mapper.toDto(responseRPC.getCustomer());
    }

    public OrderDTO lockPlaceOrder() {
        final var r = orderStub.placeOrder(PlaceStepRPC.newBuilder().setAction(TwoPCActionRPC.LOCK).build());
        return mapper.toDto(r.getOrder());
    }

    public CustomerDTO commitCustomerDebit() {
        final var responseRPC = customerStub.debitCustomer(DebitStepRPC.newBuilder().setAction(TwoPCActionRPC.COMMIT).build());
        return mapper.toDto(responseRPC.getCustomer());
    }

    public OrderDTO commitPlaceOrder() {
        final var r = orderStub.placeOrder(PlaceStepRPC.newBuilder().setAction(TwoPCActionRPC.COMMIT).build());
        return mapper.toDto(r.getOrder());
    }

    public CustomerDTO rollbackCustomerDebit() {
        final var responseRPC = customerStub.debitCustomer(DebitStepRPC.newBuilder().setAction(TwoPCActionRPC.ROLLBACK).build());
        return mapper.toDto(responseRPC.getCustomer());
    }

    public OrderDTO rollbackPlaceOrder() {
        final var r = orderStub.placeOrder(PlaceStepRPC.newBuilder().setAction(TwoPCActionRPC.ROLLBACK).build());
        return mapper.toDto(r.getOrder());
    }

    public CustomerDTO debitCustomer(UUID customerId, Long amount) {
        var request = mapper.toRPC(customerId, amount);

        try {
            var r = customerStub.debitCustomer(
                    DebitStepRPC.newBuilder()
                            .setAction(TwoPCActionRPC.EXECUTE)
                            .setCustomerDebit(request)
                            .build());

            return mapper.toDto(r.getCustomer());
        } catch (StatusRuntimeException e) {
            log.error("Cannot debit customer, status: {}, message: {}", e.getStatus(), e.getMessage());

            if (e.getStatus() == Status.NOT_FOUND) {
                throw new RuntimeException(
                        String.format(
                                "Customer not found, id: %s, details: %s",
                                customerId, trailersToString(e))
                );
            } else if (e.getStatus() == Status.INVALID_ARGUMENT) {
                throw new RuntimeException(
                        String.format(
                                "Invalid argument, id: %s, details: %s",
                                customerId, trailersToString(e))
                );
            }

            throw new RuntimeException("Unknown error occurred");
        }

    }

    public OrderDTO placeOrder(OrderDTO order) {
        var request = mapper.toRPC(order);
        var r = orderStub.placeOrder(
                PlaceStepRPC.newBuilder()
                        .setAction(TwoPCActionRPC.EXECUTE)
                        .setOrder(request)
                        .build());

        return mapper.toDto(r.getOrder());
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


}
