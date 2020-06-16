package org.example.saga.order.domain;

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

    public void send(PlaceOrderStep r) {
        log.info("place-order {}", r.getAction());
        jmsTemplate.convertAndSend("place-order.response", r);
    }
}