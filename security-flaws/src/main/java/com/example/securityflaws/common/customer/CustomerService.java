package com.example.securityflaws.common.customer;

import io.vavr.collection.Seq;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate parameterJdbcTemplate;

    public Option<Customer> findByIdJdbc(String customerId) {
        return repository.getById(customerId, jdbcTemplate);
    }

    public Option<Customer> findByIdParamJdbc(String customerId) {
        return repository.getById(customerId, parameterJdbcTemplate);
    }

    public Option<Customer> findById(UUID customerId) {
        return repository.getById(customerId);
    }

    public Customer debitBudget(Customer customer, Long amount) {
        long newBudget = customer.getBudget() - Math.abs(amount);
        if (newBudget < 0) {
            throw new InsufficientFundsException();
        }

        return repository.save(customer.setBudget(newBudget));
    }

    public Seq<Customer> findAll() {
        return repository.findAll();
    }

    public Try<Customer> createCustomer(Customer customer) {
        return Try.of(() -> repository
                .save(customer.setCreated(LocalDateTime.now())));
    }
}
