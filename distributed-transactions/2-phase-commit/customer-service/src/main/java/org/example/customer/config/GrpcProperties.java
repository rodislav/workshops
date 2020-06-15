package org.example.customer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app.grpc")
public class GrpcProperties {
    private int port = 5500;
    private boolean exitOnFail;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isExitOnFail() {
        return exitOnFail;
    }

    public void setExitOnFail(boolean exitOnFail) {
        this.exitOnFail = exitOnFail;
    }
}
