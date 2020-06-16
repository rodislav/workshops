package org.example.saga.coordinator.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.saga.api.*;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderFacade {

    private final JMSClient jmsClient;

    public void startPlacingOrder(OrderDTO order) {
        debitCustomer(order);
    }

    public void debitCustomer(OrderDTO order) {
        try {
            jmsClient.send(DebitCustomerRequest.execute(order));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new OrderPlacementException(order, e.getMessage());
        }
    }

    public void next(DebitCustomerResponse response) {
        switch (response.getAction()) {
            case EXECUTE:
                placeOrder(response.getOrder());
                break;
            case ERROR:
                log.error("Customer debit failed, customerId: {}", response.getOrder().getCustomerId());
                break;
            case ROLLBACK:
                log.warn("Customer debit rolled back, customerId: {}", response.getOrder().getCustomerId());
                break;
        }
    }

    public void next(PlaceOrderResponse response) {
        switch (response.getAction()) {
            case EXECUTE:
                log.info("Order placed successfully, customerId: {}", response.getOrder().getCustomerId());
                break;
            case ERROR:
                log.error("Placing order failed, customerId: {}", response.getOrder().getCustomerId());
                rollbackCustomerDebit(response.getOrder());
                break;
            case ROLLBACK:
                log.warn("Order rolled back, customerId: {}", response.getOrder().getCustomerId());
                break;
        }
    }

    public void placeOrder(OrderDTO order) {
        try {
            jmsClient.send(PlaceOrderRequest.execute(order));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            rollbackCustomerDebit(order);
            throw new OrderPlacementException(order, e.getMessage());
        }

    }

    private void rollbackCustomerDebit(OrderDTO orderDTO) {
        try {
            jmsClient.send(DebitCustomerRequest.rollback(orderDTO));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}