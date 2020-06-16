package org.example.saga.coordinator.domain;

import lombok.extern.slf4j.Slf4j;
import org.example.saga.api.DebitCustomerRequest;
import org.example.saga.api.PlaceOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Sender {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void send(DebitCustomerRequest r) {
        log.info("place-order-customer {}", r.getAction());
        jmsTemplate.convertAndSend("place-order-customer", r.getAction());
    }

    public void send(PlaceOrderRequest r) {
        log.info("place-order {}", r.getAction());
        jmsTemplate.convertAndSend("place-order", r.getAction());
    }
}