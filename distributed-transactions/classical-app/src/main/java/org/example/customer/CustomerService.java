package org.example.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    public Customer findById(UUID customerId) {
        return repository
                .findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

    public void debitBudget(Customer customer, Long amount) {
        customer.setBudget(customer.getBudget() - Math.abs(amount));

        if(customer.getBudget() < 0) {
            throw new InsufficientFundsException();
        }

        repository.save(customer);
    }

    public void creditBudget(UUID customerId, Long amount) {
        var customer = findById(customerId);
        customer.setBudget(customer.getBudget() + Math.abs(amount));
        repository.save(customer);
    }

    public Iterable<Customer> findAll() {
        return repository.findAll();
    }

    public UUID createCustomer(Customer customer) {
        customer.setCreated(LocalDateTime.now());
        return repository
                .save(customer)
                .getId();
    }
}
