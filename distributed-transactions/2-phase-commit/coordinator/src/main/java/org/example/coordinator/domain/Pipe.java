package org.example.coordinator.domain;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.function.Consumer;

@Slf4j
public class Pipe {

    private int count;
    private Consumer<Pipe>[] c;
    private Pipe next;

    public void start() {
        Arrays.asList(c).forEach(callbackConsumer -> callbackConsumer.accept(this));
    }

    public Pipe then(Consumer<Pipe>... c) {
        count = c.length;
        this.c = c;

        next = new Pipe();
        return next;
    }

    public void trigger() {
        count--;

        if(count < 1) {
            next.start();
        }
    }
}
