package org.example.coordinator.domain;

import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.coordinator.api.OrderDTO;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderFacade {

    private final GrpcClient grpcClient;

    //Programmatic transaction management
    //https://docs.spring.io/spring/docs/3.0.0.M3/reference/html/ch11s06.html
    //https://www.baeldung.com/spring-programmatic-transaction-management
    public Try<OrderDTO> placeOrder(OrderDTO order) {
        return Try
                .of(() -> grpcClient.lockCustomerDebit())
                .map(c -> grpcClient.lockPlaceOrder())
                .map(c -> grpcClient.debitCustomer(order.getCustomerId(), order.getAmount()))
                .map(c -> grpcClient.placeOrder(order))
                .map(c -> grpcClient.commitCustomerDebit())
                .map(c -> grpcClient.commitPlaceOrder())
                .onFailure(RuntimeException.class, e -> {
                    // grpcClient.undoCustomerDebit();
                    // grpcClient.undoPlaceOrder();
                    log.error("Failed to place an order, order: {}", order);
                    throw new OrderPlacementException(e.getMessage());
                });
    }
}
