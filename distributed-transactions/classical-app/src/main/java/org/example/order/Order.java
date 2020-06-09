package org.example.order;

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

// this is a workaround because:
// ORDER is a reserved keyword, http://www.h2database.com/html/advanced.html#keywords
// but SQL allow underscores in table names and prohibits having keywords with underscores, https://stackoverflow.com/a/19758863/1107450
@Table(name = "ORDER_")

public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private UUID customerId;

    private Long amount;
    private OrderStatus status;

    private LocalDateTime created;
    private LocalDateTime placed;
    private LocalDateTime failed;
    private String message;
}
