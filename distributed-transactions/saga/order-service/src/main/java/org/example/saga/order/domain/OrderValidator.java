package org.example.saga.order.domain;

import org.springframework.stereotype.Component;

@Component
public class OrderValidator {

    public void validateNew(Order order) {
        var e = new OrderNotValidException();

        if (order.getId() != null) {
            e.add(new OrderNotValidException.Detail("id", "ID Cannot have value"));
        }

        if (order.getCustomerId() == null) {
            e.add(new OrderNotValidException.Detail("customerId", "Customer ID cannot be null"));
        }

        if (order.getAmount() == null) {
            e.add(new OrderNotValidException.Detail("amount", "Amount should have an numeric value"));
        } else if (order.getAmount() < 0) {
            e.add(new OrderNotValidException.Detail("amount", "Amount cannot have negative value"));
        }

        if (e.hasIssues()) {
            throw e;
        }
    }

    public OrderNotValidException unknownCustomer() {
        var detail = new OrderNotValidException.Detail("customerId", "Unknown customer.");
        return new OrderNotValidException(detail);
    }
}
