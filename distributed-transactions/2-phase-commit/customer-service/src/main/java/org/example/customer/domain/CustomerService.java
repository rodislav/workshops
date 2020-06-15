package org.example.customer.domain;

import io.vavr.collection.Seq;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    public Option<Customer> findById(UUID customerId) {
        return repository.getById(customerId);
    }

    public Customer debitBudget(UUID customerId, Long amount) {
        final var customer = findById(customerId)
                .getOrElseThrow(() -> new CustomerNotFoundException(customerId));

        long newBudget = customer.getBudget() - Math.abs(amount);
        if (newBudget < 0) {
            throw new InsufficientFundsException();
        }

        customer.setBudget(newBudget);
        return repository.save(customer);
    }

    public Seq<Customer> findAll() {
        return repository.findAll();
    }

    public Try<Customer> createCustomer(Customer customer) {
        return Try.of(() -> {
            customer.setCreated(LocalDateTime.now());
            return repository.save(customer);
        });
    }
}
