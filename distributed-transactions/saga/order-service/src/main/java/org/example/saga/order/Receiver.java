package org.example.saga.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;

@Slf4j
public class Receiver {

    @JmsListener(destination = "place-order")
    public void receive(String message) {
        log.info("received message='{}'", message);

    }
}