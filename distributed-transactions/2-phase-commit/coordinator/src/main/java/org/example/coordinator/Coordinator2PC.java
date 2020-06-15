package org.example.coordinator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Coordinator2PC {
    public static void main(String[] args) {
        SpringApplication.run(Coordinator2PC.class, args);
    }
}
