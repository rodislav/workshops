package org.example.saga.order.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.saga.api.Action;
import org.example.saga.api.PlaceOrderStep;
import org.example.saga.order.api.dto.OrderMapper;
import org.example.saga.order.domain.JMSClient;
import org.example.saga.order.domain.OrderFacade;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import static org.example.saga.api.Action.PLACE_ORDER;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderJMSController {

    private final JMSClient jmsClient;
    private final OrderFacade facade;
    private final OrderMapper mapper;

    @JmsListener(destination = "place-order")
    public void receive(PlaceOrderStep step) {
        log.info("received message='{}'", step.getAction());

        if (step.getAction() == PLACE_ORDER) {
            final var order = mapper.toEntity(step.getOrder());
            facade.placeOrder(order)
                    .onSuccess(o -> {
                        jmsClient.send(PlaceOrderStep.placeOrderOk(mapper.toDto(o)));
                    })
                    .onFailure(e -> {
                        log.error(e.getMessage());
                        jmsClient.send(PlaceOrderStep.placeOrderError(step.getOrder()));
                    });
        }
    }
}