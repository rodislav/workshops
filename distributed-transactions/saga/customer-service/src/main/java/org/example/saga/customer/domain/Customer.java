package org.example.saga.customer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

