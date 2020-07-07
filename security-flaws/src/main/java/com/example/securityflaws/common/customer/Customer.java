package com.example.securityflaws.common.customer;

import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;

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
//https://vladmihalcea.com/uuid-identifier-jpa-hibernate/
public class Customer {
    String firstName;
    String lastName;
    LocalDateTime created;
    Long budget;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "uuid-char") // https://stackoverflow.com/a/42485612/1107450,
    private UUID id;
}
