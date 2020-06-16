package org.example.saga.customer.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.saga.api.DebitCustomerRequest;
import org.example.saga.customer.domain.CustomerService;
import org.example.saga.customer.domain.JMSClient;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerJMSController {

    private final CustomerService service;
    private final JMSClient jmsClient;

    @JmsListener(destination = "place-order-customer")
    public void placeOrderCustomer(DebitCustomerRequest request) {
        log.info("received message='{}'", request.getAction());

        switch (request.getAction()) {
            case EXECUTE:
                try {
                    service.debitBudget(request.getOrder().getCustomerId(), request.getOrder().getAmount());
                    jmsClient.send(DebitCustomerRequest.execute(request.getOrder()));
                } catch (Exception e) {
                    log.error(e.getMessage());
                    jmsClient.send(DebitCustomerRequest.error(request.getOrder()));
                }

            case ROLLBACK:
                try {
                    service.creditBudget(request.getOrder().getCustomerId(), request.getOrder().getAmount());
                    jmsClient.send(DebitCustomerRequest.rollback(request.getOrder()));
                } catch (Exception e) {
                    log.error(e.getMessage());
                    jmsClient.send(DebitCustomerRequest.error(request.getOrder()));
                }

        }

    }
}