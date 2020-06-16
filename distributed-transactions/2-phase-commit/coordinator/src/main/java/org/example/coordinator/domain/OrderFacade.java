package org.example.coordinator.domain;

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
    private final GrpcClientAsync grpcClientAsync;

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