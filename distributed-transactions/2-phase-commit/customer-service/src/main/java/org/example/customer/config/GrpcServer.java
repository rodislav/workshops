package org.example.customer.config;

import io.grpc.ServerBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.customer.api.GrpcController;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class GrpcServer {

    private final GrpcProperties properties;
    private final GrpcController controller;

    @PostConstruct
    public void init() {
        final var server = ServerBuilder.forPort(properties.getPort())
                .addService(controller)
                .build();

        try {
            server.start();
            log.info("gRPC Server started on port: {}", properties.getPort());
        } catch (IOException e) {
            log.error("Cannot start gRPC server, e: {}", e.getMessage());
            log.debug("Cannot start gRPC server, exception details: ", e);

            if(properties.isExitOnFail()) {
                log.warn("gRPC was marked as exitOnFail, shutting down");
                System.exit(5500);
            }
        }
    }

}
