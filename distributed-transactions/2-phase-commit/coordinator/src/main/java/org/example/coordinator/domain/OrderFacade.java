package org.example.coordinator.domain;

import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.coordinator.api.OrderDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;

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
                .of(() -> grpcClient.debitCustomer(order.getCustomerId(), order.getAmount()))
                .map(c -> grpcClient.doPlaceOrder(order))
                .onFailure(RuntimeException.class, e -> {
                    log.error("Failed to place an order, order: {}", order);
                    throw new OrderPlacementException(e.getMessage());
                });
    }
}
