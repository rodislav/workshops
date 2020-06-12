package org.example.order.domain;

import lombok.*;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "ORDERS")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //https://vladmihalcea.com/uuid-identifier-jpa-hibernate/
    private UUID id;
    private UUID customerId;

    private Long amount;
    private OrderStatus status;

    private LocalDateTime created;

    @Nullable
    private LocalDateTime placed;

    @Nullable
    private LocalDateTime failed;

    @Nullable
    private String message;
}
