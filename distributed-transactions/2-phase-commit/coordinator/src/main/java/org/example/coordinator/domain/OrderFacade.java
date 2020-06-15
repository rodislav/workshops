package org.example.coordinator.domain;

import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.coordinator.api.OrderDTO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderFacade {

    private final GrpcMapper mapper;
    private final GrpcClient grpcClient;
    private final GrpcClientAsync grpcClientAsync;

    //Programmatic transaction management
    //https://docs.spring.io/spring/docs/3.0.0.M3/reference/html/ch11s06.html
    //https://www.baeldung.com/spring-programmatic-transaction-management
    public Try<OrderDTO> placeOrder(OrderDTO order) {
        return Try
                .of(() -> grpcClient.lockCustomerDebit(order.getCustomerId()))
                .map(c -> grpcClient.lockPlaceOrder())
                .map(c -> grpcClient.debitCustomer(order.getCustomerId(), order.getAmount()))
                .map(c -> grpcClient.placeOrder(order))
                .map(c -> grpcClient.commitCustomerDebit())
                .map(c -> grpcClient.commitPlaceOrder())
                .onFailure(RuntimeException.class, e -> {
                    grpcClient.rollbackCustomerDebit();
                    grpcClient.rollbackPlaceOrder();
                    log.error("Failed to place an order, order: {}", order);
                    throw new OrderPlacementException(e.getMessage());
                });
    }

    @Async
    public void placeOrderAsync(OrderDTO order) {
        final var orderFlow = new OrderFlow(order, grpcClientAsync, mapper);
        final var customerFlow = new CustomerFlow(grpcClientAsync, mapper, order.getCustomerId(), order.getAmount());

        try {
            customerFlow.init();
            orderFlow.init();

            Pipe pipe = new Pipe();
            pipe
                    .then((c) -> customerFlow.lock(c))
                    .then((c) -> orderFlow.lock(c))
                    .then((c) -> customerFlow.execute(c))
                    .then((c) -> orderFlow.execute(c))
                    .then((c) -> customerFlow.commit(c))
                    .then((c) -> orderFlow.commit(c))
                    .then((c) -> customerFlow.end(c))
                    .then((c) -> orderFlow.end(c));

            pipe.start();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            orderFlow.rollback(null);
            customerFlow.rollback(null);
        }
    }
}