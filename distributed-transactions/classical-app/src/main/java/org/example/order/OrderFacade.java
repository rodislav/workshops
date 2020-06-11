package org.example.order;

import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.customer.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.TransactionTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderFacade {
    private final TransactionTemplate transactionTemplate;
    private final OrderValidator orderValidator;
    private final CustomerService customerService;
    private final OrderService orderService;

    public Try<Order> placeOrder(Order order) {
        return Try
                .of(() -> transactionTemplate.execute(status -> {
                    orderValidator.validateNew(order);

                    final var newOrder = orderService.createOrder(order);

                    return customerService.findById(order.getCustomerId())
                            .map(c -> customerService.debitBudget(c, newOrder.getAmount()))
                            .map(c -> orderService.placeOrder(newOrder))
                            .getOrElseThrow(orderValidator::unknownCustomer);

                }))
                .onFailure(TransactionException.class, e -> {
                    log.error("Failed to place an order, order: {}", order);
                    throw new OrderPlacementException(order, e);
                });
    }
}
