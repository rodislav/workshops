package org.example.saga.customer.domain;

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

    public void send(PlaceOrderStep step) {
        log.info("place-order-customer {}", step.getAction());
        jmsTemplate.convertAndSend("place-order.response", step);
    }
}