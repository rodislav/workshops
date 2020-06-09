package com.example.demo;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.io.IOException;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class GrpcConfiguration {

    private Server server;

    private final GrpcProperties grpcProperties;
    private final GreeterGrpcController greeterGrpcController;

    @Bean
    Server getGrpcServer() throws IOException {
        Server server = ServerBuilder
                .forPort(grpcProperties.getPort())
                .addService(greeterGrpcController)
                .build();

        if (grpcProperties.isEnabled()) {
            log.info("Starting GRPC Server on port: " + grpcProperties.getPort());
            server.start();
        } else {
            log.info("GRPC Server was not enabled");
        }

        this.server = server;

        return server;
    }

    @PreDestroy
    public void preDestroy() {
        try {
            this.server.shutdownNow();
        } catch (Throwable ignored) {
        }
    }
}
