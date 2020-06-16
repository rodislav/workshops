package org.example.saga.order.domain;

import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderFacade {
    private final OrderValidator orderValidator;
    private final OrderService orderService;

    //Programmatic transaction management
    //https://docs.spring.io/spring/docs/3.0.0.M3/reference/html/ch11s06.html
    //https://www.baeldung.com/spring-programmatic-transaction-management
    public Try<Order> placeOrder(Order order) {
        return Try
                .of(() -> {
                    orderValidator.validateNew(order);

                    final var newOrder = orderService.createOrder(order);

                    return orderService.placeOrder(newOrder);
                })
                .onFailure(TransactionException.class, e -> {
                    log.error("Failed to place an order, order: {}", order);
                    throw new OrderPlacementException(order, e);
                });
    }
}
