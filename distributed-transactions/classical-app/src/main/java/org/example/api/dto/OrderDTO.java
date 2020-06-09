package org.example.api.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.order.Order;
import org.example.order.OrderStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
public class OrderDTO {

    private UUID id;

    private String customerId;

    private Long amount;
    private OrderStatus status;

    private LocalDateTime created;
    private LocalDateTime placed;

    public static OrderDTO fromEntity(Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .customerId(order.getCustomerId())
                .amount(order.getAmount())
                .status(order.getStatus())
                .created(order.getCreated())
                .placed(order.getPlaced())
                .build();
    }

    public Order toEntity() {
        return Order.builder()
                .id(id)
                .customerId(customerId)
                .amount(amount)
                .status(status)
                .created(created)
                .placed(placed)
                .build();
    }
}
