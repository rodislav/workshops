package org.example.saga.customer.domain;

import lombok.extern.slf4j.Slf4j;
import org.example.saga.api.DebitCustomerRequest;
import org.example.saga.api.DebitCustomerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JMSClient {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void send(DebitCustomerResponse r) {
        log.info("place-order-customer {}", r.getAction());
        jmsTemplate.convertAndSend("place-order-customer.response", r);
    }
}