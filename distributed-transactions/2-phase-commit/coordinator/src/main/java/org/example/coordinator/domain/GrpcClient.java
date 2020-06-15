package org.example.coordinator.domain;

import com.google.protobuf.Empty;
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
import org.example.order.generated.grpc.OrderServiceGrpc;
import org.example.order.generated.grpc.OrderServiceGrpc.OrderServiceBlockingStub;
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
    final Empty empty = Empty.newBuilder().build();

    OrderServiceBlockingStub orderStub;
    CustomerServiceBlockingStub customerStub;

    @PostConstruct
    public void init() {
        var orderCB = ManagedChannelBuilder
                .forAddress(orderServiceParams.getHost(), orderServiceParams.getPort())
                .usePlaintext()
                .build();

        var customerCB = ManagedChannelBuilder
                .forAddress(customerServiceParams.getHost(), customerServiceParams.getPort())
                .usePlaintext()
                .build();

        orderStub = OrderServiceGrpc.newBlockingStub(orderCB);
        customerStub = CustomerServiceGrpc.newBlockingStub(customerCB);
    }

    public Empty lockCustomerDebit() {
        return customerStub.lockDebitCustomer(empty);
    }

    public Empty lockPlaceOrder() {
        return orderStub.lockPlaceOrder(empty);
    }

    public OrderDTO doPlaceOrder(OrderDTO order) {
        var request = mapper.toRPC(order);
        var orderRPC = orderStub.placeOrder(request);

        return mapper.toDto(orderRPC);
    }

    public CustomerDTO debitCustomer(UUID customerId, Long amount) {
        var request = mapper.toRPC(customerId, amount);

        try {
            var customerRPC = customerStub.debitCustomer(request);

            return mapper.toDto(customerRPC);
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
