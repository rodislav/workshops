package org.example.saga.order.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.saga.api.Action;
import org.example.saga.api.PlaceOrderRequest;
import org.example.saga.api.PlaceOrderResponse;
import org.example.saga.order.api.dto.OrderMapper;
import org.example.saga.order.domain.JMSClient;
import org.example.saga.order.domain.Order;
import org.example.saga.order.domain.OrderFacade;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderJMSController {

    private final JMSClient jmsClient;
    private final OrderFacade facade;
    private final OrderMapper mapper;

    @JmsListener(destination = "place-order")
    public void receive(PlaceOrderRequest request) {
        log.info("received message='{}'", request.getAction());

        if (request.getAction() == Action.EXECUTE) {
            final var order = mapper.toEntity(request.getOrder());
            facade.placeOrder(order)
                    .onSuccess(o -> {
                        jmsClient.send(PlaceOrderResponse.execute(mapper.toDto(o)));
                    })
                    .onFailure(e -> {
                        log.error(e.getMessage());
                        jmsClient.send(PlaceOrderResponse.error(request.getOrder()));
                    });
        }
    }
}