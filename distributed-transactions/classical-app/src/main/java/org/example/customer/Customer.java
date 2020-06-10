package org.example.customer;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Table(name = "CUSTOMERS")
public class Customer {
    String firstName;
    String lastName;
    LocalDateTime created;
    Long budget;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
}
