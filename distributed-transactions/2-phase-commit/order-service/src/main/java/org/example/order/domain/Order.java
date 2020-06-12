package org.example.order.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
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
    private LocalDateTime placed;
    private LocalDateTime failed;
    private String message;
}
