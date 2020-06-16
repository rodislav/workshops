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
    private final GrpcClient grpcClient;

    @Async
    public void placeOrderAsync(OrderDTO order) {
        final var orderFlow = new OrderFlow(order, grpcClient, mapper);
        final var customerFlow = new CustomerFlow(grpcClient, mapper, order.getCustomerId(), order.getAmount());

        try {
            customerFlow.init();
            orderFlow.init();

            Pipe pipe = new Pipe();
            pipe.then(customerFlow::lock)
                    .then(orderFlow::lock)
                    .then(customerFlow::execute)
                    .then(orderFlow::execute)
                    .then(customerFlow::commit)
                    .then(orderFlow::commit)
                    .then(customerFlow::end)
                    .then(orderFlow::end);

            pipe.start();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            orderFlow.rollback(null);
            customerFlow.rollback(null);

            throw new OrderPlacementException(order, e.getMessage());
        }
    }
}