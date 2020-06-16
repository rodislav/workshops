package org.example.saga.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;

@Slf4j
public class Receiver {

    @JmsListener(destination = "place-order-customer")
    public void receive(String message) {
        log.info("received message='{}'", message);

    }
}