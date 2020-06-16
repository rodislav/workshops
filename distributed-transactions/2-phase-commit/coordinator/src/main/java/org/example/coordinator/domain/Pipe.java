package org.example.coordinator.domain;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
public class Pipe {

    private Consumer<Pipe> c;
    private Pipe next;

    public void start() {
        c.accept(this);
    }

    public Pipe then(Consumer<Pipe> c) {
        this.c = c;

        next = new Pipe();
        return next;
    }

    public void trigger() {
        if(next != null) {
            next.start();
        }
    }
}
