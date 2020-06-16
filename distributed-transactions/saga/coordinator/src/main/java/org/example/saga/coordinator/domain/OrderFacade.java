package org.example.saga.coordinator.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.saga.api.*;
import org.springframework.stereotype.Service;

import static org.example.saga.api.PlaceOrderStep.debitCustomerRollback;

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
            jmsClient.sendToCustomer(PlaceOrderStep.debitCustomer(order));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new OrderPlacementException(order, e.getMessage());
        }
    }

    public void next(PlaceOrderStep step) {
        switch (step.getAction()) {
            case DEBIT_CUSTOMER_OK:
                placeOrder(step.getOrder());
                break;
            case PLACE_ORDER_OK:
                log.info("Order placed, orderId: {}", step.getOrder().getId());
                break;
            case DEBIT_CUSTOMER_ERROR:
                log.error("Customer debit failed, customerId: {}", step.getOrder().getCustomerId());
                break;
            case PLACE_ORDER_ERROR:
                log.error("Placing order failed, customerId: {}", step.getOrder().getCustomerId());
                rollbackCustomerDebit(step.getOrder());
                break;
            case DEBIT_CUSTOMER_ROLLBACK_OK:
                log.info("Customer debit rolled back, customerId: {}", step.getOrder().getCustomerId());
                break;
            case DEBIT_CUSTOMER_ROLLBACK_ERROR:
                log.error("Failed rolling back customer debit, customerId: {}", step.getOrder().getCustomerId());
                break;
        }
    }

    public void placeOrder(OrderDTO order) {
        try {
            jmsClient.sendToOrder(PlaceOrderStep.placeOrder(order));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            rollbackCustomerDebit(order);
            throw new OrderPlacementException(order, e.getMessage());
        }

    }

    private void rollbackCustomerDebit(OrderDTO orderDTO) {
        try {
            jmsClient.sendToCustomer(debitCustomerRollback(orderDTO));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}