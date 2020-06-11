package org.example.order;

import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api.dto.CustomerDTO;
import org.example.api.dto.OrderDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderFacade {

    //Programmatic transaction management
    //https://docs.spring.io/spring/docs/3.0.0.M3/reference/html/ch11s06.html
    //https://www.baeldung.com/spring-programmatic-transaction-management
    public Try<OrderDTO> placeOrder(OrderDTO order) {
        return Try
                .of(() -> debitBudget(order.getCustomerId(), order.getAmount()))
                .map(c -> doPlaceOrder(order))
                .onFailure(TransactionException.class, e -> {
                    log.error("Failed to place an order, order: {}", order);
                    throw (e);
                });
    }

    private CustomerDTO debitBudget(UUID customerId, Long amount) {
        //  rest call
        return null;
    }

    private OrderDTO doPlaceOrder(OrderDTO order) {
        //  rest call
        return null;
    }
}
