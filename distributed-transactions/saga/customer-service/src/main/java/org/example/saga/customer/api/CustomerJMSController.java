package org.example.saga.customer.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.saga.api.PlaceOrderStep;
import org.example.saga.customer.domain.CustomerService;
import org.example.saga.customer.domain.JMSClient;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import static org.example.saga.api.PlaceOrderStep.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerJMSController {

    private final CustomerService service;
    private final JMSClient jmsClient;

    @JmsListener(destination = "place-order-customer")
    public void placeOrderCustomer(PlaceOrderStep step) {
        log.info("received message='{}'", step.getAction());

        switch (step.getAction()) {
            case DEBIT_CUSTOMER:
                try {
                    service.debitBudget(step.getOrder().getCustomerId(), step.getOrder().getAmount());
                    jmsClient.send(debitCustomerOk(step.getOrder()));
                } catch (Exception e) {
                    log.error(e.getMessage());
                    jmsClient.send(debitCustomerError(step.getOrder()));
                }
                break;

            case DEBIT_CUSTOMER_ROLLBACK:
                try {
                    service.creditBudget(step.getOrder().getCustomerId(), step.getOrder().getAmount());
                    jmsClient.send(debitCustomerRollbackOk(step.getOrder()));
                } catch (Exception e) {
                    log.error(e.getMessage());
                    jmsClient.send(debitCustomerRollbackError(step.getOrder()));
                }
                break;

        }

    }
}