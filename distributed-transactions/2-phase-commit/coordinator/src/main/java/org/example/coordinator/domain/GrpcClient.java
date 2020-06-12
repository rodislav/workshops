package org.example.coordinator.domain;

import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import org.example.coordinator.api.CustomerDTO;
import org.example.coordinator.api.OrderDTO;
import org.example.coordinator.config.CustomerServiceParams;
import org.example.coordinator.config.OrderServiceParams;
import org.example.order.generated.grpc.CustomerServiceGrpc;
import org.example.order.generated.grpc.OrderServiceGrpc;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GrpcClient {

    private final GrpcMapper mapper;
    private final CustomerServiceParams customerServiceParams;
    private final OrderServiceParams orderServiceParams;

    OrderServiceGrpc.OrderServiceBlockingStub orderStub;
    CustomerServiceGrpc.CustomerServiceBlockingStub customerStub;

    @PostConstruct
    public void init() {
        var orderCB = ManagedChannelBuilder
                .forAddress(orderServiceParams.getHost(), orderServiceParams.getPort())
                .build();

        var customerCB = ManagedChannelBuilder
                .forAddress(customerServiceParams.getHost(), customerServiceParams.getPort())
                .build();

        orderStub = OrderServiceGrpc.newBlockingStub(orderCB);
        customerStub = CustomerServiceGrpc.newBlockingStub(customerCB);
    }


    public OrderDTO doPlaceOrder(OrderDTO order) {
        var request = mapper.toRPC(order);
        var orderRPC = orderStub.placeOrder(request);

        return mapper.toDto(orderRPC);
    }

    public CustomerDTO debitCustomer(UUID customerId, Long amount) {
        var request = mapper.toRPC(customerId, amount);
        var customerRPC = customerStub.debitCustomer(request);

        return mapper.toDto(customerRPC);
    }


}
