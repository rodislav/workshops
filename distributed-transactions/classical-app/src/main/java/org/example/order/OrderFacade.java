package org.example.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.customer.Customer;
import org.example.customer.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderFacade {

    private final TransactionTemplate transactionTemplate;
    private final OrderValidator orderValidator;
    private final CustomerService customerService;
    private final OrderService orderService;

    public UUID placeOrder(Order order) {
        AtomicReference<Boolean> amountDebited = new AtomicReference<>(false);
        try {
            return transactionTemplate.execute(status -> {
                orderValidator.validateNew(order);

                final Customer customer = customerService.findById(order.getCustomerId());

                Order newOrder = orderService.createOrder(order);
                customerService.debitBudget(customer, newOrder.getAmount());
                amountDebited.set(true);

                newOrder = orderService.placeOrder(order);

                return newOrder.getId();
            });
        } catch (TransactionException e) {
            Order newOrder = orderService.markAsFailed(order, e.getMessage());

            if(amountDebited.get()) {
                customerService.creditBudget(order.getCustomerId(), order.getAmount());
            }

            log.error("Failed to place an order, order: {}", newOrder);
        }

        throw new OrderPlacementException(order);
    }

}
