package org.example.saga.coordinator.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.saga.api.Action;
import org.example.saga.api.DebitCustomerResponse;
import org.example.saga.api.PlaceOrderResponse;
import org.example.saga.coordinator.domain.OrderFacade;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderJMSController {

    private final OrderFacade facade;

    @JmsListener(destination = "place-order-customer.response")
    public void customerResponse(DebitCustomerResponse response) {
        log.info("received message='{}'", response.getAction());
        facade.next(response);
    }

    @JmsListener(destination = "place-order.response")
    public void orderResponse(PlaceOrderResponse response) {
        log.info("received message='{}'", response.getAction());
        facade.next(response);
    }
}