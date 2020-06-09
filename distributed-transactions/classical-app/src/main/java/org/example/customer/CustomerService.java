package org.example.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    public Optional<Customer> findById(String customerId) {
        return repository.findById(UUID.fromString(customerId));
    }
}
