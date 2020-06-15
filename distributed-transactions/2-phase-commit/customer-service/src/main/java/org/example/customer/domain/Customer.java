package org.example.customer.domain;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CUSTOMERS")
//https://vladmihalcea.com/uuid-identifier-jpa-hibernate/
public class Customer {
    String firstName;
    String lastName;
    LocalDateTime created;
    Long budget;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
}

