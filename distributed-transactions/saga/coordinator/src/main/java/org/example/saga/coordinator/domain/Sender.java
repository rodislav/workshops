package org.example.saga.coordinator.domain;

import org.example.saga.api.DebitCustomerRequest;
import org.example.saga.api.PlaceOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

public class Sender {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void send(DebitCustomerRequest r) {
        jmsTemplate.convertAndSend("place-order-customer", r.getAction());
    }

    public void send(PlaceOrderRequest r) {
        jmsTemplate.convertAndSend("place-order", r.getAction());
    }
}