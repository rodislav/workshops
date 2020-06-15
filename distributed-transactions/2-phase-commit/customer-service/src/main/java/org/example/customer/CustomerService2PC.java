package org.example.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class CustomerService2PC {
    public static void main(String[] args) {
        SpringApplication.run(CustomerService2PC.class, args);
    }
}
