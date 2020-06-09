package org.example.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.order.Order;
import org.example.order.OrderStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private UUID id;

    private UUID customerId;

    private Long amount;
    private OrderStatus status;

    private LocalDateTime created;
    private LocalDateTime placed;
    private LocalDateTime failed;
    private String message;

    public static OrderDTO fromEntity(Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .customerId(order.getCustomerId())
                .amount(order.getAmount())
                .status(order.getStatus())
                .created(order.getCreated())
                .placed(order.getPlaced())
                .failed(order.getFailed())
                .message(order.getMessage())
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
                .failed(failed)
                .message(message)
                .build();
    }
}
