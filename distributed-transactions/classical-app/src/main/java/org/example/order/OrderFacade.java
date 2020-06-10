package org.example.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.customer.Customer;
import org.example.customer.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderFacade {

    private final TransactionTemplate transactionTemplate;
    private final OrderValidator orderValidator;
    private final CustomerService customerService;
    private final OrderService orderService;

    public UUID placeOrder(Order order) {
        try {
            return transactionTemplate.execute(status -> {
                orderValidator.validateNew(order);

                var customer = customerService.findById(order.getCustomerId());

                var newOrder = orderService.createOrder(order);
                customerService.debitBudget(customer, newOrder.getAmount());

                newOrder = orderService.placeOrder(order);

                return newOrder.getId();
            });
        } catch (TransactionException e) {
            log.error("Failed to place an order, order: {}", order);
            throw new OrderPlacementException(order, e);
        }

    }

}
