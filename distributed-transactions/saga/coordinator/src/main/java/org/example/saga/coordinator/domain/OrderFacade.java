package org.example.saga.coordinator.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.saga.api.OrderDTO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderFacade {

    private final Sender sender;
    private final Receiver receiver;

    @Async
    public void placeOrderAsync(OrderDTO order) {
        try {
            CustomerFlow customerFlow = new CustomerFlow(order.getCustomerId(), order.getAmount(), sender, receiver);
            OrderFlow orderFlow = new OrderFlow(order, sender, receiver);

            customerFlow.doLock();
            orderFlow.doLock();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new OrderPlacementException(order, e.getMessage());
        }
    }
}