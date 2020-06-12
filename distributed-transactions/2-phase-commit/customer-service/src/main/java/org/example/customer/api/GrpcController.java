package org.example.customer.api;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.example.customer.api.dto.CustomerMapper;
import org.example.customer.domain.CustomerService;
import org.example.order.generated.grpc.CustomerServiceGrpc;
import org.example.order.generated.grpc.CustomerServiceOuterClass.CustomerDebitRPC;
import org.example.order.generated.grpc.CustomerServiceOuterClass.CustomerRPC;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GrpcController extends CustomerServiceGrpc.CustomerServiceImplBase {

    private final CustomerMapper mapper;
    private final CustomerService service;

    @Override
    public void debitCustomer(CustomerDebitRPC request, StreamObserver<CustomerRPC> responseObserver) {
        try {
            final var customer = service.debitBudget(
                    UUID.fromString(request.getCustomerId()),
                    request.getAmount());

            responseObserver.onNext(mapper.toRPC(customer));
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}
