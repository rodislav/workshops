package org.example.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.customer.Customer;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    private UUID id;
    String firstName;
    String lastName;
    LocalDateTime created;
    Long budget;

    public static CustomerDTO fromEntity(Customer customer) {
        return CustomerDTO.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .created(customer.getCreated())
                .budget(customer.getBudget())
                .build();
    }

    public Customer toEntity() {
        return Customer.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .created(created)
                .budget(budget)
                .build();
    }
}
