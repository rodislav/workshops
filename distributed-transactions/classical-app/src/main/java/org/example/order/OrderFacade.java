package org.example.order;

import lombok.RequiredArgsConstructor;
import org.example.customer.Customer;
import org.example.customer.CustomerService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderFacade {

    private final OrderValidator orderValidator;
    private final CustomerService customerService;
    private final OrderService orderService;

    public UUID placeOrder(Order order) {
        final Optional<Customer> customer = customerService.findById(order.getCustomerId());
        orderValidator.validateNew(order, customer);

        customerService.recordNewOrder();
        orderService.createOrder();
    }

}
