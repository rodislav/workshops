package org.example.saga.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Receiver {

    @JmsListener(destination = "place-order-customer")
    public void receive(String message) {
        log.info("received message='{}'", message);

    }
}