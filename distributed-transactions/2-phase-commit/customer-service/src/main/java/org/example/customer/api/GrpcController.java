package org.example.customer.api;

import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.customer.api.dto.CustomerMapper;
import org.example.customer.domain.CustomerNotFoundException;
import org.example.customer.domain.CustomerService;
import org.example.customer.domain.InsufficientFundsException;
import org.example.customer.generated.grpc.CustomerServiceGrpc;
import org.example.customer.generated.grpc.DebitStepRPC;
import org.example.customer.generated.grpc.DebitStepResponseRPC;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class GrpcController extends CustomerServiceGrpc.CustomerServiceImplBase {

    private final CustomerMapper mapper;
    private final CustomerService service;

    @Override
    public void debitCustomer(DebitStepRPC request, StreamObserver<DebitStepResponseRPC> responseObserver) {
        try {
            switch (request.getAction()) {
                case LOCK:
                    // todo lock
                    try {
                        UUID customerId = UUID.fromString(request.getCustomerDebit().getCustomerId());
                    } catch (IllegalArgumentException e) {
                        responseObserver.onError(new StatusRuntimeException(Status.INVALID_ARGUMENT, getSingleMeta("CustomerId")));
                        return;
                    }
                    responseObserver.onCompleted();
                    break;

                case EXECUTE:
                    UUID customerId = UUID.fromString(request.getCustomerDebit().getCustomerId());
                    final var customer = service.debitBudget(customerId, request.getCustomerDebit().getAmount());

                    responseObserver.onNext(DebitStepResponseRPC.newBuilder()
                            .setCustomer(mapper.toRPC(customer))
                            .build());
                    responseObserver.onCompleted();
                    break;

                case COMMIT:
                    // todo actual commit
                    responseObserver.onCompleted();
                    break;

                case ROLLBACK:
                    responseObserver.onCompleted();
                    // todo rollback
                    break;
            }

        } catch (CustomerNotFoundException e) {
            responseObserver.onError(new StatusRuntimeException(Status.NOT_FOUND));
        } catch (InsufficientFundsException e) {
            responseObserver.onError(new StatusRuntimeException(Status.INVALID_ARGUMENT, getSingleMeta("InsufficientFunds")));
        } catch (Exception e) {
            log.error(e.getMessage());
            log.debug(e.getMessage(), e);
            responseObserver.onError(new StatusRuntimeException(Status.INTERNAL));
        }
    }

    private Metadata getSingleMeta(String value) {
        Metadata m = new Metadata();
        m.put(Metadata.Key.of("code", Metadata.ASCII_STRING_MARSHALLER), value);
        return m;
    }
}
