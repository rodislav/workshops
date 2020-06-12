package org.example.customer.domain;

import io.vavr.collection.Seq;
import io.vavr.control.Option;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.UUID;

public interface CustomerRepository extends CrudRepository<Customer, UUID> {
    Option<Customer> getById(UUID id);

    @NonNull
    @Override
    Seq<Customer> findAll();
}
