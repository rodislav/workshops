package org.example;

import com.fasterxml.jackson.databind.Module;
import io.vavr.jackson.datatype.VavrModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ClassicalApp {

    public static void main(String[] args) {
        SpringApplication.run(ClassicalApp.class, args);
    }

    @Bean
    Module vavrModule() {
        return new VavrModule();
    }
}
