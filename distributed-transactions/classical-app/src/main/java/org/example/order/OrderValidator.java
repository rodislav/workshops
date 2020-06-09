package org.example.order;

import org.example.customer.Customer;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderValidator {

    public void validateNew(Order order, Optional<Customer> c) {
        OrderNotValidException e = new OrderNotValidException();

        if(order.getId() != null) {
            e.add(new OrderNotValidException.Detail("ID Cannot have value"));
        }

        if(order.getAmount() == null) {
            e.add(new OrderNotValidException.Detail("Amount should have an numeric value"));
        }

        c.ifPresentOrElse(
                customer -> {},
                () -> e.add(new OrderNotValidException.Detail("Customer ID is not valid")));

        if(e.hasIssues()) {
            throw e;
        }
    }

}
