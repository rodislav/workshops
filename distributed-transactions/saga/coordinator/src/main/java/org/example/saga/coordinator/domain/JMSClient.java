package org.example.saga.coordinator.domain;

import lombok.extern.slf4j.Slf4j;
import org.example.saga.api.PlaceOrderStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JMSClient {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendToCustomer(PlaceOrderStep r) {
        log.info("place-order-customer {}", r.getAction());
        jmsTemplate.convertAndSend("place-order-customer", r);
    }

    public void sendToOrder(PlaceOrderStep step) {
        log.info("place-order {}", step.getAction());
        jmsTemplate.convertAndSend("place-order", step);
    }
}