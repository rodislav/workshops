package org.example.saga.coordinator.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.saga.api.PlaceOrderStep;
import org.example.saga.coordinator.domain.OrderFacade;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderJMSController {

    private final OrderFacade facade;

    @JmsListener(destination = "place-order.response")
    public void customerResponse(PlaceOrderStep step) {
        log.info("received message='{}'", step.getAction());
        facade.next(step);
    }
}